package com.ts.Controller;

import com.ts.Model.Entity.Friend;
import com.ts.Model.Entity.Result;
import com.ts.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/links")
    public Result getLinks() {
        return friendService.getFriendList();
    }

    @DeleteMapping("/links/{id}")
    public Result deleteLink(@PathVariable("id") int id) {
        return friendService.deleteFriend(id);
    }

    @PutMapping("/links")
    public Result updateLink(@RequestBody Friend friend) {
        return friendService.updateFriend(friend);
    }

    @PostMapping("/links")
    public Result addLink(@RequestBody Friend friend) {
        return friendService.addFriend(friend);
    }
}
