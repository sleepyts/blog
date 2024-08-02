package com.ts.Service.Impl;

import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.Friend;
import com.ts.Entity.Result;
import com.ts.Mapper.FriendMapper;
import com.ts.Service.FriendService;
import com.ts.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ts.Constants.RedisConstants.FRIEND_CACHE_KEY;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private RedisService redisService;

    @Override
    @Cacheable(KEY = FRIEND_CACHE_KEY)
    @RequestLog
    public Result getFriendList() {
        return Result.success(friendMapper.selectList(null));
    }

    @Override
    @RequestLog
    public Result addFriend(Friend friend) {
        friend.setUpdateTime(LocalDateTime.now());
        friend.setCreateTime(LocalDateTime.now());
        redisService.delayDeleteTwice(FRIEND_CACHE_KEY);
        return friendMapper.insert(friend) > 0 ? Result.success() : Result.error("");
    }

    @Override
    @RequestLog
    public Result deleteFriend(int id) {
        boolean suc = friendMapper.deleteById(id) > 0;
        redisService.delayDeleteTwice(FRIEND_CACHE_KEY);
        return suc ? Result.success() : Result.error("");
    }

    @Override
    @RequestLog
    public Result updateFriend(Friend friend) {
        boolean suc = friendMapper.updateById(friend) > 0;
        redisService.delayDeleteTwice(FRIEND_CACHE_KEY);
        return suc ? Result.success() : Result.error("");
    }

}
