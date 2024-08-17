//package com.ts;
//
//
//import org.apache.naming.factory.BeanFactory;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//
//@SpringBootTest
//class test1111ApplicationTest {
//    @Test
//    void contextLoads(int []nums) {
//        int[] dp = new int[nums.length];
//        dp[0] = 1;
//        int ans = 1;
//        for (int i = 1; i < nums.length; i++) {
//            dp[i] = 1;
//            for (int j = 0; j < i; j++) {
//                if (nums[i] > nums[j]) {
//                    dp[i] = Math.max(dp[i], dp[j] + 1);
//                }
//            }
//        }
//        for (int i = 0; i < nums.length; i++) {
//            ans = Math.max(ans, dp[i]);
//        }
//        System.out.println(ans);
//    }
//}