package com.ts.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 扣子 AI 工具
 */
@Component
@Setter
public class CozeAiUtil {
    private static final String API_URL = "https://api.coze.cn/v3/chat";

    @Value("${config.coze.aiId}")
    private  String aiId;
    @Value("${config.coze.token}")
    private   String AUTH_TOKEN;

    public  String getBlogSummary(String blogContent) throws IOException {
        // 创建请求体
        RequestBody requestBody = new RequestBody(aiId, "123", true, true,
                new Message("user", blogContent, "text"));
        // 将请求体转换为 JSON
        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);
        // 发送请求
        return sendChat(jsonBody);
    }


    private String sendChat(String jsonBody) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // 使用 CompletableFuture 以异步方式处理响应
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(response -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
                        String line;
                        String eventType = null;
                        StringBuilder eventData = new StringBuilder();
                        Gson gson=new Gson();
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("event:")) {
                                eventType = line.substring(6).trim();
                            } else if (line.startsWith("data:")) {
                                eventData.append(line.substring(5).trim());
                            } else if (line.isBlank()) {
                                // 事件结束，解析 data 部分
                                if (eventType != null && eventData.length() > 0) {
                                    // 如果是最终回复事件，直接返回数据
                                    if (eventType.equals("conversation.message.completed")) {
                                        return (gson.fromJson(String.valueOf(eventData), JsonObject.class).get("content").getAsString());
                                    }
                                    eventData.setLength(0); // 重置 StringBuilder
                                }
                                eventType = null;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null; // 如果没有找到完成事件，返回 null
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return "Error occurred: " + e.getMessage(); // 处理异常情况
                })
                .join(); // 等待结果完成
    }

    // 定义请求对象
    public static class RequestBody {
        @SerializedName("bot_id")
        private String botId;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("stream")
        private boolean stream;
        @SerializedName("auto_save_history")
        private boolean autoSaveHistory;
        @SerializedName("additional_messages")
        private Message[] additionalMessages;

        public RequestBody(String botId, String userId, boolean stream, boolean autoSaveHistory, Message... additionalMessages) {
            this.botId = botId;
            this.userId = userId;
            this.stream = stream;
            this.autoSaveHistory = autoSaveHistory;
            this.additionalMessages = additionalMessages;
        }
    }

    // 定义消息对象
    public static class Message {
        private String role;
        private String content;
        @SerializedName("content_type")
        private String contentType;

        public Message(String role, String content, String contentType) {
            this.role = role;
            this.content = content;
            this.contentType = contentType;
        }
    }
}
