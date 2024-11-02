package com.ts.Controller;

import am.ik.webhook.HmacWebhookSigner;
import am.ik.webhook.WebhookSigner;
import am.ik.webhook.WebhookVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@RestController
@Slf4j
public class GithubWebHookController {

    private static String gitHubSecret="asdkjalskjdlkasjlk";

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveGithubPush(@RequestBody String payload,
                                                    @RequestHeader("X-Hub-Signature-256") String signature) {
        try {
            try{
                verify(signature, payload);
            }catch (Exception e){
                log.info("Invalid signature.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
            }
            log.info("Signature verified successfully.");

            // 执行 git pull
            ProcessBuilder gitPull = new ProcessBuilder("/usr/bin/git", "pull");
            gitPull.directory(new java.io.File("/root/java/blog")); // 指定仓库路径
            Process process = gitPull.start();

            // 读取命令行输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();


        } catch (Exception e) {
            log.error("Failed to process webhook", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process webhook: " + e.getMessage());
        }
        return ResponseEntity.ok("Webhook received and processed");
    }
    private static void verify(String signature, String body) {
        HmacWebhookSigner webhookSigner = new HmacWebhookSigner("SHA256", gitHubSecret);
        WebhookVerifier verifier = new WebhookVerifier(webhookSigner, WebhookSigner.Encoder.HEX);
        verifier.verify(body, signature);
    }
}
