package com.callv2.dns.manager.infrastructure.external.discord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscordWebhookNotifier {

    private final String webhookUrl;

    public DiscordWebhookNotifier(@Value("${discord.webhook-url}") String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendMessage(String message) {

        if (!isValidDiscordWebhookUrl(webhookUrl)) {
            throw new IllegalArgumentException("Invalid Discord webhook URL");
        }

        try {
            WebClient.create()
            .post()
            .uri(webhookUrl)
            .header("Content-Type", "application/json")
            .bodyValue("{\"content\": \"" + message + "\"}")
            .retrieve()
            .bodyToMono(String.class)
            .subscribe();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to Discord webhook", e);
        }
    }

    private boolean isValidDiscordWebhookUrl(String url) {
        
        if (url != null && url.startsWith("https://discord.com/api/webhooks/")) {
            try {
                WebClient.create()
                .get()
                .uri(url)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}