package com.ts.Service;


import java.util.List;

public interface RedisService {

    <T> void setList(String key, List<T> list);

    <T> void setList(String key, List<T> list,Long expireTime);

    <T> List<T> getList(String key);

    void delayDeleteTwice(String key);

    void delayDeleteTwice(String ... keys);

    void delayDeleteTwice(List<String> keys);

    void delete(String key);

    void set(String key,Object value);

    void set(String key,Object value,Long expireTime);

    <T> T get(String key);

    String getString(String key);
}
