package com.ts.Controller.Admin;


import com.ts.Entity.Admin;
import com.ts.Entity.Result;
import com.ts.Service.IAdminService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin, HttpSession session,
                        @RequestHeader(value = "Authorization", required = false) String token){
        return adminService.login(admin,session);
    }
}
