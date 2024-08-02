package com.ts.Utils;

import com.ts.Entity.Admin;
import com.ts.Entity.Visitor;

public class Holder {
    private static final ThreadLocal<Admin> tl = new ThreadLocal<Admin>();
    private static final ThreadLocal<Visitor> currentVisitor = new ThreadLocal<Visitor>();

    public static void setCurrentVisitor(Visitor visitor) {
        currentVisitor.set(visitor);
    }

    public static Visitor getCurrentVisitor() {
        return currentVisitor.get();
    }

    public static void removeCurrentVisitor() {
        currentVisitor.remove();
    }

    public static void setAdmin(Admin admin) {
        tl.set(admin);
    }

    public static Admin getAdmin() {
        return tl.get();
    }

    public static void removeAdmin() {
        tl.remove();
    }
}
