package com.ts.Service.Impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.Comment;
import com.ts.Entity.DTO.CommentDTO;
import com.ts.Entity.Result;
import com.ts.Entity.VO.CommentVO;
import com.ts.Entity.VO.PageVO;
import com.ts.Mapper.CommentMapper;
import com.ts.Service.ICommentService;
import com.ts.Service.RedisService;
import com.ts.Utils.RegexUtil;
import com.ts.Utils.VerifyUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ts.Constants.RedisConstants.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 添加评论
     * @param commentDTO: CommentDTO
     * return: Result<String> error: 验证码错误,验证码过期,请刷新重试,?    success: null
     */
    @Override
    @RequestLog
    public Result addComment(CommentDTO commentDTO) {
        //进行验证码校验
        int ok= VerifyUtil.verify(commentDTO.getVerify(),redisTemplate);

        if(ok==1) return Result.error("?");
        if(ok==2) return Result.error("验证码过期,请刷新重试");
        if(ok==3) return Result.error("验证码错误");

        // 保存评论
        Comment comment =new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUrl(comment.getUrl().toLowerCase());
        if(!RegexUtil.isHttps(comment.getUrl())) comment.setUrl(null);
        save(comment);

        //删除缓存
        deleteCache(comment.getBlogId());
        String verifyKey=VERIFY_CACHE_KEY+commentDTO.getVerify().getKey();
        redisTemplate.delete(verifyKey);
        return Result.success();
    }

    /**
     * 获取评论列表 在管理界面中调用
     * @param id: Long
     * @return: Result<List<CommentVO>>
     */
    @Override
    public Result getCommentById(Long id) {
        List<CommentVO> commentVOList = new ArrayList<>();
//        List<String> jsons=redisTemplate.opsForList().range(COMMENT_CACHE_KEY + id, 0, -1);
//        if (jsons!= null && !jsons.isEmpty()) {
//            for (String json : jsons) {
//                CommentVO commentVO = JSONUtil.toBean(json, CommentVO.class);
//                commentVOList.add(commentVO);
//            }
//            return Result.success(commentVOList);
//        }
//        RLock lock=redissonClient.getLock(COMMENT_CACHE_LOCK+id);
//        Boolean isLockable=lock.tryLock();
        List<Comment> commentList = query().eq("blog_id", id).list();
        for (Comment comment : commentList) {
            fromEntToVO(commentVOList, comment);
        }
        commentVOList.sort((o1, o2) -> {
            LocalDateTime createTime1 = o2.getCreateTime();
            LocalDateTime createTime2 = o1.getCreateTime();
            return createTime1.compareTo(createTime2);
        });
//
//        try {
//            if (!isLockable) {
//                return Result.error("获取评论失败，请稍后再试");
//            }
//
//            if (!commentVOList.isEmpty())  {
//                redisTemplate.opsForList().rightPushAll(COMMENT_CACHE_KEY + id, commentVOList.stream().map(JSONUtil::toJsonStr).toArray(String[]::new));
//                redisTemplate.expire(COMMENT_CACHE_KEY + id, Duration.ofSeconds(COMMENT_CACHE_EXPIRE_TIME));
//            }
//        } catch (BeansException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
        return Result.success(commentVOList);
    }

    /**
     * 删除评论
     * @param id: Long
     * @return: Result<String>
     */
    public Result deleteCommentById(Long id) {
        // id of comment
        Long blogId = query().eq("id", id).one().getBlogId();
//        redisTemplate.delete(COMMENT_CACHE_KEY + blogId);
        removeById(id);

        // delete reply comments
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply_id", id);
        remove(queryWrapper);

        deleteCache(blogId);
        return Result.success();
    }

    /**
     * 根据博客id删除评论 在删除博客时调用
     * @param blogId: Long
     * @return: Result<String>
     */
    public Result deleteCommentByBlogId(Long blogId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        List<Comment> commentList = list(queryWrapper);
        if(commentList==null || commentList.isEmpty()) return Result.success();

//        for (Comment comment : commentList) {
//            String imgUrl = comment.getImgUrl();
//            if (imgUrl != null && !imgUrl.isEmpty()) {
//                String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
//                ossUtil.deleteFile(fileName);
//            }
//        }
        remove(queryWrapper);
        deleteCache(blogId);
        return Result.success();
    }

    /**
     * Get comment by page
     * @param id the id of blog
     * @param current current page number
     * @param size page size
     * @return Result<PageVO>
     */
    @RequestLog
    @Override
    public Result getCommentByPage(Long id, Integer current, Integer size) {
        List<CommentVO> commentVOList = new ArrayList<>();
        String cacheKey = COMMENT_CACHE_KEY + id + ":" + current + ":" + size;
        String lockKey = COMMENT_CACHE_LOCK + id + ":" + current + ":" + size;

        // 尝试从缓存中获取数据
        PageVO pageVO = redisService.get(cacheKey);
        if (pageVO != null) {
            return Result.success(pageVO);
        }

        // 获取分布式锁
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLockable = lock.tryLock();

        PageVO pageEntity;
        try {
            if (!isLockable) {
                return Result.error("获取评论失败，请稍后再试");
            }
            long total=query().eq("blog_id", id).eq("reply_id", -1).count();
            // 从数据库中查询数据
            Page<Comment> page = new Page<>(current, size);
            Page<Comment> comments = page(page, new QueryWrapper<Comment>()
                    .eq("blog_id", id)
                    .eq("reply_id", -1)
                    .orderByDesc("create_time"));

            // 将查询结果转换为VO对象
            for (Comment comment : comments.getRecords()) {
                if(comment.getUrl()==null) comment.setUrl("");
                fromEntToVO(commentVOList, comment);
            }

            // 构建返回的PageVO对象
            pageEntity = new PageVO();
            pageEntity.setTotal(total);
            pageEntity.setRows(commentVOList);
            // 存入缓存
            if (!commentVOList.isEmpty()) {
                redisService.set(cacheKey,pageEntity,COMMENT_CACHE_EXPIRE_TIME);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 确保释放锁
            lock.unlock();
        }

        return Result.success(pageEntity);
    }

    /**
     * transform entity to vo
     * @param commentVOList the list of vo
     * @param comment the entity of comment
     */
    private void fromEntToVO(List<CommentVO> commentVOList, Comment comment) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        List<Comment> replyList = query().eq("reply_id", comment.getId()).list();
        List<CommentVO> replyVOList = new ArrayList<>();
        for (Comment reply : replyList) {
            CommentVO replyVO = new CommentVO();
            BeanUtils.copyProperties(reply, replyVO);
            replyVO.setReplyList(null);
            replyVOList.add(replyVO);
        }
        replyVOList.sort((o1, o2) -> {
            LocalDateTime replyCreateTime1 = o2.getCreateTime();
            LocalDateTime replyCreateTime2 = o1.getCreateTime();
            return replyCreateTime1.compareTo(replyCreateTime2);
        });
        commentVO.setReplyList(replyVOList);
        commentVOList.add(commentVO);
    }

    /**
     * delete redis cache of comments
     * @param blogId the id of blog
     */
    private void deleteCache(Long blogId) {
        String pattern = COMMENT_CACHE_KEY + blogId + ":*";
        Set<String> keys = new HashSet<>();
        try (Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(redisConnection ->
                redisConnection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build()))) {
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during scanning keys", e);
        }
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}