package com.callv2.dns.manager.infrastructure.record.event;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventHandler;
import com.callv2.dns.manager.domain.record.DnsRecordIpChangedEvent;
import com.callv2.dns.manager.infrastructure.external.discord.DiscordWebhookNotifier;

@Component
public class DnsRecordIpChangedEventDiscordWebhookNotificationHandler
        implements EventHandler<DnsRecordIpChangedEvent.Data> {

    private final DiscordWebhookNotifier discordWebhookNotifier;

    public DnsRecordIpChangedEventDiscordWebhookNotificationHandler(
            final DiscordWebhookNotifier discordWebhookNotifier) {
        this.discordWebhookNotifier = discordWebhookNotifier;
    }

    @Override
    public String eventName() {
        return DnsRecordIpChangedEvent.create("", null).name();
    }

    @Override
    public void handle(Event<DnsRecordIpChangedEvent.Data> event) {
        discordWebhookNotifier.sendMessage(
                String.format("🛠️ Registro DNS `%s` atualizado para o IP `%s`.",
                        event.data().dns(),
                        event.data().newIP()));
    }

}
