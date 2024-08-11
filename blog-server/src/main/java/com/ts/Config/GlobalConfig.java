package com.ts.Config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.global")
public class GlobalConfig {

    private String indexUrl;
    private String indexName;
}

