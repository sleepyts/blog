package com.ts.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync(proxyTargetClass=true)
public class AsyncConfig {
}
