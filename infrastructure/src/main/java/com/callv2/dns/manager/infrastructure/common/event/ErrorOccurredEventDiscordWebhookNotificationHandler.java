package com.callv2.dns.manager.infrastructure.common.event;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.domain.common.event.DomainErrorOccurredEvent;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventHandler;
import com.callv2.dns.manager.infrastructure.external.discord.DiscordWebhookNotifier;

@Component
public class ErrorOccurredEventDiscordWebhookNotificationHandler
        implements EventHandler<DomainErrorOccurredEvent.Data> {

    private final DiscordWebhookNotifier discordWebhookNotifier;

    public ErrorOccurredEventDiscordWebhookNotificationHandler(
            final DiscordWebhookNotifier discordWebhookNotifier) {
        this.discordWebhookNotifier = discordWebhookNotifier;
    }

    @Override
    public String eventName() {
        return DomainErrorOccurredEvent.create("", null).name();
    }

    @Override
    public void handle(Event<DomainErrorOccurredEvent.Data> event) {
        discordWebhookNotifier.sendMessage("❌ Ocorreu um erro: `%s`. %s"
                .formatted(
                        event.data().message(),
                        String.join(";", event.data().errors())));
    }
}
