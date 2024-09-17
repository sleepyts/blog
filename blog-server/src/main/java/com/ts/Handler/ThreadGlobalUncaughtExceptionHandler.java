package com.ts.Handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadGlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    private static final ThreadGlobalUncaughtExceptionHandler instance = new ThreadGlobalUncaughtExceptionHandler();

    private ThreadGlobalUncaughtExceptionHandler() {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread {} ", t.getName(), e);
    }

    public static ThreadGlobalUncaughtExceptionHandler getInstance() {
        return instance;
    }
}
