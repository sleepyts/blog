package com.ts.Utils;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.ts.Mapper.VisitorMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@SuppressWarnings("all")
public class IpBloomFilter {

    @Autowired
    private VisitorMapper visitorMapper;

    // 布隆过滤器的大小和错误率
    private final int size = 50000; // 50000 elements
    private final double errorRate = 0.001; // 0.1% error rate


    private BloomFilter<String> allIpFilter;
    private BloomFilter<String> dailyIpFilter;


    @PostConstruct
    public void init() {
        dailyIpFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, errorRate);
        allIpFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, errorRate);
        List<String> ips = visitorMapper.selectAllIps();
        for (String ip : ips) allIpFilter.put(ip);
    }

    public void databaseAdd(String ip) {
        allIpFilter.put(ip);
    }

    public boolean databaseContain(String ip) {
        return allIpFilter.mightContain(ip);
    }

    public void dailyAdd(String ip) {
        dailyIpFilter.put(ip);
    }

    public boolean dailyContain(String ip) {
        return dailyIpFilter.mightContain(ip);
    }

    public void clearDaily() {
        dailyIpFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, errorRate);
    }
}
