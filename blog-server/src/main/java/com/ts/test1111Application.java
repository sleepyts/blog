package com.ts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.ts.Mapper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableScheduling
public class test1111Application {

    public static void main(String[] args) {
        SpringApplication.run(test1111Application.class, args);
    }
}
