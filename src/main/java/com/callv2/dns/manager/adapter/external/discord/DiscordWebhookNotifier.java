package com.callv2.dns.manager.adapter.external.discord;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordWebhookNotifier {

    private final String webhookUrl;

    public DiscordWebhookNotifier(@Value("${discord.webhook-url}") String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendMessage(String message) {
        if (!isValidDiscordWebhookUrl(webhookUrl))
            return;

        try {
            HttpURLConnection connection = openConnection(webhookUrl);
            String jsonPayload = buildJsonPayload(message);

            sendPayload(connection, jsonPayload);
            validateResponse(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDiscordWebhookUrl(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();

            return host != null &&
                    (host.equals("discord.com") || host.equals("discordapp.com")) &&
                    path != null &&
                    path.startsWith("/api/webhooks/");
        } catch (Exception e) {
            return false;
        }
    }

    private HttpURLConnection openConnection(String url) throws Exception {
        URI webhook = new URI(url);
        HttpURLConnection connection = (HttpURLConnection) webhook.toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.addRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private String buildJsonPayload(String message) {
        return String.format("{\"content\": \"%s\"}", message.replace("\"", "\\\""));
    }

    private void sendPayload(HttpURLConnection connection, String payload) throws Exception {
        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void validateResponse(HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        if (responseCode != 204) {
            System.err.println("Falha ao enviar webhook. Código: " + responseCode);
        }
    }
}