package com.callv2.dns.manager.adapter.dns;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.cloudflare.CloudflareDnsManagerClient;
import com.callv2.dns.manager.adapter.notification.DiscordWebhookNotifier;
import com.callv2.dns.manager.domain.record.RecordGateway;

@Component
@Profile({ "cloudflare" })
public class CloudflareRecordGateway implements RecordGateway {

    private final CloudflareDnsManagerClient cloudflareDnsManagerClient;
    private final DiscordWebhookNotifier discordWebhookNotifier;

    public CloudflareRecordGateway(
            final CloudflareDnsManagerClient cloudflareDnsManagerClient,
            final DiscordWebhookNotifier discordWebhookNotifier) {
        this.cloudflareDnsManagerClient = cloudflareDnsManagerClient;
        this.discordWebhookNotifier = discordWebhookNotifier;
    }

    @Override
    public void update(final com.callv2.dns.manager.domain.record.Record record) {

        this.cloudflareDnsManagerClient.updateDns(
                record.getName().value(),
                record.getType().getValue(),
                record.getIp().getValue());

        discordWebhookNotifier.sendMessage(
                String.format("🛠️ Registro DNS `%s` atualizado para o IP `%s`.",
                        record.getName().value(),
                        record.getIp().getValue()));
    }

}
