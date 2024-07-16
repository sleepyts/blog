package com.ts.Utils;


import jakarta.annotation.PostConstruct;
import jodd.util.StringUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ip地址工具类
 * @author sleepyts
 * @date 2024-06-29
 */
@Component
public class ipAddressUtils {
    @Value("${config.ipXbd.path}")
    private String ipXbdPath;

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";

    private static Searcher searcher;


    /**
     * 获取客户端ip地址
     * @param ip getHeader("X-Forwarded-For")
     * @param ip1 getHeader("X-Real-IP")
     * @param ip2 request.getRemoteAddr()
     * @return 客户端ip地址
     */
    public static String getClientIp(String ip,String ip1,String ip2) {
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = ip1;
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = ip2;
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 初始化 ip2region.db
     */
    @PostConstruct
    public void init() {
        // 从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(ipXbdPath);
            searcher = new Searcher(null, null, cBuff);
        } catch (Exception e) {
            System.out.printf("failed to load content from `%s`: %s\n", ipXbdPath, e);
            return;
        }
    }
    /**
     * 根据ip从 ip2region.db 中获取地理位置
     * @param ip ip
     * @return String
     */
    public static String getCityInfo(String ip) {
        if(ip.equals(LOCALHOST_IP)) return "内网IP";
        if(ip.equals(LOCALHOST)) return "本地";
        if(StringUtil.isEmpty(ip)) return UNKNOWN;
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }
       return "";
    }
}
