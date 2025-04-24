package com.callv2.dns.manager.adapter.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class DiscordWebhookNotifier {

    private final String webhookUrl;

    public DiscordWebhookNotifier(@Value("${discord.webhook-url}") String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendMessage(String message) {
        try {
            URI uri = new URI(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");

            String jsonPayload = String.format("{\"content\": \"%s\"}", message.replace("\"", "\\\""));

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            if (connection.getResponseCode() != 204) {
                System.err.println("Falha ao enviar webhook.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
