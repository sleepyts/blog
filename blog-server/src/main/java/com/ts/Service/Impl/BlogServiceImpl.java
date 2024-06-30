package com.ts.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.Blog;
import com.ts.Entity.Result;
import com.ts.Entity.VO.BlogThumbnailVO;
import com.ts.Entity.VO.BlogVO;
import com.ts.Entity.VO.PageVO;
import com.ts.Mapper.BlogMapper;
import com.ts.Service.IBlogService;
import com.ts.Service.ICommentService;
import com.ts.Service.IRecordService;
import com.ts.Service.RedisService;
import com.ts.Utils.LocalDateUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ts.Constants.RedisConstants.*;


@Service
class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RedissonClient redissonClient;

    /*
     * 查询博客列表
     * return Result<List<BlogVO>>
     */
    @Override
    @RequestLog
    @Transactional()
    public Result queryBlog() {
        List<BlogVO> blogVOList;
        String key = BLOG_CACHE_KEY;
        blogVOList=redisService.getList(key);
        if (blogVOList != null && !blogVOList.isEmpty()) {
            for (BlogVO blogVO : blogVOList) {
                Long count = Long.valueOf(commentService.query().eq("blog_id", blogVO.getId()).count());
                blogVO.setCommentNum(count);
                String categoryName = blogMapper.getCategoryName(Long.valueOf(blogVO.getCategoryId()));
                blogVO.setCategoryName(categoryName);
            }
            return Result.success(blogVOList);
        }

        blogVOList = new ArrayList<>();
        RLock lock = redissonClient.getLock(BLOG_CACHE_LOCK);
        // 加锁
        boolean isLockable = lock.tryLock();

        try {
            //如果未成功获取锁，则说明有其他线程正在查询，则直接返回 防止缓存重复添加
            if (!isLockable) return Result.error("当前访问人数过多，请稍后再试");
            List<Blog> blogs = query().orderByDesc("create_time").list();
            for (Blog blog : blogs) {
                BlogVO blogVO = new BlogVO();
                BeanUtils.copyProperties(blog, blogVO);
                blogVO.setDate(LocalDateUtil.transfromDate(blog.getCreateTime().toLocalDate(), LocalDateUtil.BLOG_CARD_DATE_FORMAT));
                blogVOList.add(blogVO);
            }
            if (!blogVOList.isEmpty()) {
                redisService.setList(key, blogVOList, BLOG_CACHE_EXPIRE_TIME);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        if (!blogVOList.isEmpty()) {
            for (BlogVO blogVO : blogVOList) {
                Long count = Long.valueOf(commentService.query().eq("blog_id", blogVO.getId()).count());
                blogVO.setCommentNum(count);
                String categoryName = blogMapper.getCategoryName(Long.valueOf(blogVO.getCategoryId()));
                blogVO.setCategoryName(categoryName);
            }
        }
        return Result.success(blogVOList);
    }

    public Result getBlogContent(Integer id) {
        String key = BLOG_CONTENT_CACHE_KEY + id;
        String content =redisService.getString(key);
        if (content != null) {
            return Result.success(content);
        }
        RLock lock = redissonClient.getLock(BLOG_CACHE_LOCK);
        // 加锁
        boolean isLockable = lock.tryLock();
        try {
            if (!isLockable) return Result.error("当前访问人数过多，请稍后再试");
            Blog blog = getById(id);
            if (blog == null) {
                return Result.error("博客不存在");
            }
            content = blog.getContent();
            if (content != null && !content.isEmpty()) redisService.set(key, content, BLOG_CONTENT_CACHE_EXPIRE_TIME);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return Result.success(content);
    }


    /*
     * 添加博客
     * @param blog 博客实体
     */
    @Override
    @Transactional
    public Result addBlog(Blog blog) {
        blog.setCreateTime(LocalDateTime.now());
        blog.setUpdateTime(LocalDateTime.now());
        save(blog);
        recordService.addRecord(blog);
        redisService.delayDeleteTwice(BLOG_CACHE_KEY);
        return Result.success();
    }

    /*
     * 更新博客
     * @param blog 博客实体
     */
    @Override
    @Transactional
    public Result updateBlog(Blog blog) {
        blog.setUpdateTime(LocalDateTime.now());
        updateById(blog);
        recordService.updateRecord(blog);
        redisService.delayDeleteTwice(BLOG_CACHE_KEY, BLOG_CONTENT_CACHE_KEY +blog.getId());
        return Result.success();
    }

    /*
     * 删除博客 同时删除博客对应的评论和记录

     * @param id 博客ID
     */
    @Override
    @Transactional
    public Result deleteBlog(Long id) {
        // To delete the file from OSS
        //It has been abandoned because the files are uploaded to other place.

//        String imgUrl = blog.getImg();
//
//        if (imgUrl != null && !imgUrl.isEmpty()) {
//            String imgFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
//            ossUtil.deleteFile(imgFileName);
//        }
//        String contentUrl = blog.getContent();
//        if (contentUrl != null && !contentUrl.isEmpty()) {
//            String contentFileName = contentUrl.substring(contentUrl.lastIndexOf("/") + 1);
//            ossUtil.deleteFile(contentFileName);
//        }
        removeById(id);
        commentService.deleteCommentByBlogId(id);
        recordService.deleteRecordByBlogId(id);

        redisService.delayDeleteTwice(BLOG_CACHE_KEY, BLOG_CONTENT_CACHE_KEY +id);
        return Result.success();
    }

    /*
     * 分页查询博客列表
     * @param page 页码
     * @param size 页容量
     * @return Result<List<BlogVO>>
     */
    @Override
    @RequestLog
    public Result getCommentByPage(Integer current, Integer size) {
        Page<Blog> page = new Page<>(current, size);
        Page<Blog> blogs = page(page, new QueryWrapper<Blog>().orderByDesc("create_time"));
        List<BlogVO> blogVOList = blogs.getRecords().stream()
                .map(blog -> {
                    BlogVO blogVO = new BlogVO();
                    BeanUtils.copyProperties(blog, blogVO);
                    blogVO.setDate(LocalDateUtil.transfromDate(blog.getCreateTime().toLocalDate(), LocalDateUtil.BLOG_CARD_DATE_FORMAT));
                    return blogVO;
                })
                .collect(Collectors.toList());
        PageVO pageEntity = new PageVO();
        pageEntity.setTotal(blogs.getTotal());
        pageEntity.setRows(blogVOList);
        return Result.success(pageEntity);
    }

    @Override
    public Result getBlogThumbnail() {
        List<BlogThumbnailVO> blogThumbnailVOList = new ArrayList<>();
        List<Blog> blogs = query().orderByDesc("create_time").list();
        for (Blog blog : blogs) {
            BlogThumbnailVO blogThumbnailVO = new BlogThumbnailVO();
            blogThumbnailVO.setValue(blog.getId());
            blogThumbnailVO.setLabel(blog.getTitle());
            blogThumbnailVOList.add(blogThumbnailVO);
        }
        return Result.success(blogThumbnailVOList);
    }

}