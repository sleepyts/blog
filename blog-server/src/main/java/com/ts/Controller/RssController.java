//package com.ts.Controller;
//
//import com.rometools.rome.feed.synd.*;
//import com.rometools.rome.io.FeedException;
//import com.rometools.rome.io.SyndFeedOutput;
//import com.ts.Entity.Settings;
//import com.ts.Service.IBlogService;
//import com.ts.Service.SettingsService;
//import com.ts.VO.BlogVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@Deprecated
//public class RssController {
//
//    @Autowired
//    private SettingsService settingsService;
//    @Autowired
//    private IBlogService blogService;
//
//    @GetMapping("/rss")
//    public ResponseEntity<String> getRss() {
//        Settings setting = (Settings) settingsService.getSettings().getData();
//        SyndFeed feed = new SyndFeedImpl();
//        feed.setFeedType("rss_2.0");
//        feed.setTitle(setting.getIndexName());
//        feed.setDescription(setting.getDescription());
//        feed.setLink(setting.getIndexUrl());
//
//        List<SyndEntry> items = new ArrayList<>();
//        List<BlogVO> blogList = (List<BlogVO>) blogService.getBlogList().getData();
//        for (BlogVO blog : blogList) {
//            LocalDateTime localDateTime = blog.getCreateTime();
//
//            // 指定时区（可以根据需要选择不同的时区）
//            ZoneId zoneId = ZoneId.systemDefault();
//
//            // 将LocalDateTime与ZoneId结合，创建一个ZonedDateTime
//            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
//
//            // 将ZonedDateTime转换为Instant
//            Date date = Date.from(zonedDateTime.toInstant());
//
//            SyndEntry entry = new SyndEntryImpl();
//            entry.setTitle(blog.getTitle());
//            entry.setLink(setting.getIndexUrl() + "/Blog/" + blog.getId());
//            entry.setAuthor(setting.getIndexName());
//            entry.setPublishedDate(date);
//
//            SyndContent description = new SyndContentImpl();
//            description.setValue(blog.getDescription());
//            entry.setDescription(description);
//            items.add(entry);
//        }
//
//        feed.setEntries(items);
//
//        SyndFeedOutput output = new SyndFeedOutput();
//        StringWriter writer = new StringWriter();
//        try {
//            output.output(feed, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (FeedException e) {
//            e.printStackTrace();
//        }
//        String rssFeed = writer.toString();
//
//        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//        headers.add(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/rss+xml; charset=UTF-8");
//
//        return ResponseEntity.ok().headers(headers).body(rssFeed);
//
//    }
//}
