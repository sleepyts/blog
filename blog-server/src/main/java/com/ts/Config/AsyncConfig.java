package com.ts.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync(proxyTargetClass=true)
public class AsyncConfig {
//    @Bean(name="taskExecutor")
//    public Executor taskExecutor(){
//        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("Async-");
//        executor.initialize();
//        return executor;
//    }
}
