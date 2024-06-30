package com.ts.Service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.Admin;
import com.ts.Entity.Result;
import jakarta.servlet.http.HttpSession;


public interface IAdminService extends IService<Admin> {

    Result login(Admin admin, HttpSession session);
}
