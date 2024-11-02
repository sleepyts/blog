package com.ts.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync(proxyTargetClass=true)
public class AsyncConfig implements AsyncConfigurer {
    @Autowired
    private GlobalConfig globalConfig;

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Bean(name="taskExecutor")
    @Primary
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor= globalConfig.getEnv().equals("dev")?devThreadPoolExecutor():onlineThreadPoolExecutor();
        executor.initialize();
        return executor;
    }

    private ThreadPoolTaskExecutor devThreadPoolExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("MyThreadTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    private ThreadPoolTaskExecutor onlineThreadPoolExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("MyThreadTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
