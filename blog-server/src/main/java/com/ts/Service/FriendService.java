package com.ts.Service;

import com.ts.Model.Entity.Friend;
import com.ts.Model.Entity.Result;

public interface FriendService {
    Result getFriendList();
    Result addFriend(Friend friend);
    Result deleteFriend(int id);
    Result updateFriend(Friend friend);
}
