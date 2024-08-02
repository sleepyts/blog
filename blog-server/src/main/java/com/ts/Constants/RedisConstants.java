package com.ts.Constants;

public class RedisConstants {
    public static final String PRE = "";

    public static final String BLOG_CACHE_KEY = PRE + "BLOG_CACHE";
    public static final String COMMENT_CACHE_KEY = PRE + "COMMENT_CACHE";
    public static final String VERIFY_CACHE_KEY = PRE + "VERIFY_CACHE";
    public static final String RECORD_CACHE_KEY = PRE + "RECORD_CACHE";
    public static final String APP_CACHE_KEY = PRE + "APP_CACHE";
    public static final String ABOUT_TEXT_CACHE_KEY = PRE + "ABOUT_TEXT_CACHE";
    public static final String ADMIN_LOGIN_KEY = PRE + "ADMIN_LOGIN_KEY";
    public static final String BLOG_CONTENT_CACHE_KEY = PRE + "BLOG_CONTENT_CACHE";
    public static final String PV_CACHE_KEY = PRE + "PV_CACHE";
    public static final String UV_CACHE_KEY = PRE + "UV_CACHE";
    public static final String SETTINGS_CACHE_KEY = PRE + "SETTINGS_CACHE";
    public static final String FRIEND_CACHE_KEY = PRE + "FRIEND_CACHE";
    public static final String MOMENT_CACHE_KEY = PRE + "MOMENT_CACHE";
    public static final String BLOG_LIST_CACHE_KEY = PRE + "BLOG_LIST_CACHE";
    public static final String MOMENT_LIKE_KEY = PRE + "MOMENT_LIKE_KEY";

    public static final String BLOG_CACHE_LOCK = PRE + "BLOG_CACHE_LOCK";
    public static final String COMMENT_CACHE_LOCK = PRE + "COMMENT_CACHE_LOCK";
    public static final String APP_CACHE_LOCK = PRE + "APP_CACHE_LOCK";
    public static final String RECORD_CACHE_LOCK = PRE + "RECORD_CACHE_LOCK";
    public static final String SETTINGS_CACHE_LOCK = PRE + "SETTINGS_CACHE_LOCK";

    public static final Long NORMAL_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long VERIFY_CACHE_EXPIRE_TIME = (long) 60; // 1 minutes
    public static final Long COMMENT_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long BLOG_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long RECORD_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long ADMIN_LOGIN_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long BLOG_CONTENT_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
    public static final Long APP_CACHE_EXPIRE_TIME = 60 * 60 * 24L; // 24 hours
}
