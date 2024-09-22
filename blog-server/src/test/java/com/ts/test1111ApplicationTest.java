package com.ts;

import java.awt.List;
import java.lang.reflect.Field;

import org.apache.naming.factory.BeanFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class test1111ApplicationTest {

    @Test
    void contextLoads() throws InstantiationException, IllegalAccessException {
        Integer a = 2;
        Field[] fields = a.getClass().getFields();

        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}