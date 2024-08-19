package com.ts.Utils;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.ts.Mapper.BlogMapper;
import com.ts.Mapper.VisitorMapper;
import jakarta.annotation.PostConstruct;
import lombok.Locked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@SuppressWarnings("all")
public class BloomFilters {

    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private BlogMapper blogMapper;

    // 布隆过滤器的大小和错误率
    private final int allIpSize = 50000; // 50000 elements
    private final int dailyIpSize = 5000; // 5000 elements
    private final double errorRate = 0.001; // 0.1% error rate



    private BloomFilter<String> allIpFilter;
    private AtomicReference<BloomFilter<String>> dailyIpFilter = new AtomicReference<>();

    @PostConstruct
    public void init() {
        dailyIpFilter.set( BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), dailyIpSize, errorRate));
        allIpFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), allIpSize, errorRate);
        List<String> ips = visitorMapper.selectAllIps();
        for (String ip : ips) allIpFilter.put(ip);
    }

    synchronized public void databaseAdd(String ip) {
        allIpFilter.put(ip);
    }

    public boolean databaseContain(String ip) {
        return allIpFilter.mightContain(ip);
    }

    synchronized public void dailyAdd(String ip) {
        dailyIpFilter.get().put(ip);
    }

    public boolean dailyContain(String ip) {
        return dailyIpFilter.get().mightContain(ip);
    }

    public void clearDaily() {
        dailyIpFilter.set(BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), dailyIpSize, errorRate));
    }
}
