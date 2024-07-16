package com.ts.Service;

import com.ts.Entity.Friend;
import com.ts.Entity.Result;

public interface FriendService {
    Result getFriendList();
    Result addFriend(Friend friend);
    Result deleteFriend(int id);
    Result updateFriend(Friend friend);
}
