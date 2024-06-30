package com.ts.Utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Util class for LocalDate operations.
 */
public class LocalDateUtil {
    public static final String BLOG_CARD_DATE_FORMAT = "MMMM dd, YYYY";
    public static final String COMMENT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Transform LocalDate to String with specified format. Example: "2021-08-01" to "Aug 1, 2021"
     * @param date date to be transformed
     * @param format format of the output string
     * @return transformed string
     */
    public static String transfromDate(LocalDate date, String format){
        // 创建一个自定义的日期格式，指定为英文语言环境
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);

        // 使用 DateTimeFormatter 格式化日期为字符串
        return date.format(formatter);
    }

    /**
     * Transform comment time to relative time. Example: "2021-08-01 10:00:00" to "2 days ago"
     * @param commentTime comment time to be transformed
     * @return transformed string
     * 
     */
    public static String convertToRelativeTime(Instant commentTime) {
        Instant currentTime = Instant.now();
        Duration duration = Duration.between(commentTime, currentTime);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            if(days>6)
                return transfromDate(commentTime.atZone(ZoneId.systemDefault()).toLocalDate(),
                        COMMENT_DATE_FORMAT);
            return days + "天前";
        } else if (hours > 0) {
            return hours + "小时前";
        } else if (minutes > 0) {
            return minutes + "分钟前";
        } else {
            return "刚刚";
        }
    }
}
