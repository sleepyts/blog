package com.ts.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GithubWebHookController {

    @PostMapping("/webhook")
    public String receiveGithubPush() {
        try {
            // 拉取最新代码
            ProcessBuilder gitPull = new ProcessBuilder("git", "pull");
            gitPull.directory(new java.io.File("/root/java/blog")); // 指定仓库路径
            Process process = gitPull.start();

            // 读取命令行输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();

            // 执行自定义命令
            ProcessBuilder customCommand = new ProcessBuilder("echo ok");
            Process customProcess = customCommand.start();
            customProcess.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to process webhook: " + e.getMessage();
        }
        return "Webhook received and processed";
    }
}
