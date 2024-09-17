package com.ts.Config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.global")
public class GlobalConfig {

    private String indexUrl;
    private String indexName;
    private String env;
}

