package com.ts.Utils;



public class RegexUtil {
    private static final String HTTPS_REGEX = "^https://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,}(/\\S*)?$";
    public static boolean isHttps(String url){
        return url.matches(HTTPS_REGEX);
    }
}
