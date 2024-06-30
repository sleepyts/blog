package com.ts.Utils;

import com.ts.Entity.Admin;

public class AdminHolder {
    private static final ThreadLocal<Admin> tl=new ThreadLocal<Admin>();

    public static void setAdmin(Admin admin){
        tl.set(admin);
    }

    public static Admin getAdmin() {
        return tl.get();
    }

    public static void removeAdmin(){
        tl.remove();
    }
}
