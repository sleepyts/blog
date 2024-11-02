package com.ts.Controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ts.Model.Entity.Blog;
import com.ts.Model.Entity.Result;
import com.ts.Service.IBlogService;
import com.ts.Utils.CozeAiUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class BlogController {
    private static final String API_URL = "https://api.coze.cn/v3/chat";

    @Value("${config.coze.aiId}")
    private  String aiId;
    @Value("${config.coze.token}")
    private  String AUTH_TOKEN;
    @Autowired
    private IBlogService blogService;
    @GetMapping("/Blog/page/{page}")
    public Result getBlog(@PathVariable("page") Integer page) {
        return blogService.queryBlog(page);
    }

    @GetMapping("/Blog/{id}")
    public Result getBlogDetail(@PathVariable("id") Integer id) {
        return blogService.getBlogDetail(id);
    }


    @GetMapping("/admin/Blog/content/{id}")
    public Result getBlogContent(@PathVariable("id") Integer id) {
        return blogService.getBlogContent(id);
    }

    @GetMapping("/admin/Blog")
    public Result getBlogList() {
        return blogService.getBlogList();
    }
    @GetMapping("/Blog/category/{id}")
    public Result getBlogCategory(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable("id") Integer id) {
        return blogService.getBlogByPageAndCategory(page, id);
    }
    @GetMapping("/Blog/summary/{id}")
    public SseEmitter streamSse(@PathVariable Integer id,HttpServletResponse response1) {
        // 在响应中添加自定义头
        response1.setHeader("X-Accel-Buffering", "no");
        response1.setHeader("Content-Type","text/event-stream");
        response1.setHeader("Cache-Control","no-cache");
        // 创建一个SseEmitter对象，并设置超时时间
        SseEmitter emitter = new SseEmitter(0L); // 0L表示永不超时
        CozeAiUtil.RequestBody requestBody = new CozeAiUtil.RequestBody(aiId, "123", true, true,
                new CozeAiUtil.Message("user", blogService.getBlogContent(id).getData().toString(), "text"));
        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        // 异步发送请求
        CompletableFuture<Void> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenAccept(response -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
                        String line;
                        String eventType = null;
                        StringBuilder eventData = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("event:")) {
                                eventType = line.substring(6).trim();
                            } else if (line.startsWith("data:")) {
                                eventData.append(line.substring(5).trim());
                            } else if (line.isBlank()) {
                                // 事件结束，解析 data 部分
                                if (eventType != null && !eventData.isEmpty()) {
                                    if (eventType.equals("conversation.message.delta")) {
                                        emitter.send(
                                                SseEmitter.event()
                                                .name(eventType)
                                                .data(gson
                                                        .fromJson(String.valueOf(eventData), JsonObject.class)
                                                        .get("content")
                                                        .getAsString())
                                        );
                                    }
                                    eventData.setLength(0); // 重置 StringBuilder
                                }
                                eventType = null;
                            }
                        }
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                        e.printStackTrace();
                    }
                })
                .exceptionally(e -> {
                    emitter.completeWithError(e);
                    return null;
                });

        // 处理连接完成和超时
        emitter.onCompletion(future::join);
        emitter.onTimeout(emitter::complete);

        return emitter;
    }
    @PostMapping("/admin/Blog")
    public Result addBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @PutMapping("/admin/Blog")
    public Result updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/admin/Blog/{id}")
    public Result deleteBlog(@PathVariable("id") Long id) {
        return blogService.deleteBlog(id);
    }

    @GetMapping("/admin/Blog/thumbnail")
    public Result getBlogThumb() {
        return blogService.getBlogThumbnail();
    }

    @GetMapping("/Blog/random")
    public Result getRandomBlog() {
        return blogService.getRandomBlog();
    }
    @GetMapping("/Blog/search")
    public Result searchBlog( @RequestParam(value = "keyword") String keyword) {
        return blogService.searchBlog(keyword);
    }

}
