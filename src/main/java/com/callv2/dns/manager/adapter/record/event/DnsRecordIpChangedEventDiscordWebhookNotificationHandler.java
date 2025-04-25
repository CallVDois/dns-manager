package com.callv2.dns.manager.adapter.record.event;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.external.discord.DiscordWebhookNotifier;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventHandler;
import com.callv2.dns.manager.domain.record.DnsRecordIpChangedEvent;
import com.callv2.dns.manager.domain.record.DnsRecordIpChangedEvent.Data;

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
    public void handle(Event<Data> event) {
         discordWebhookNotifier.sendMessage(
                String.format("🛠️ Registro DNS `%s` atualizado para o IP `%s`.",
                        event.data().dns(),
                        event.data().newIP()));
    }

}
