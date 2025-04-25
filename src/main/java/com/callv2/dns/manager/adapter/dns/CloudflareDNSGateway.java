package com.callv2.dns.manager.adapter.dns;

import com.callv2.dns.manager.adapter.notification.DiscordWebhookNotifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.cloudflare.CloudflareDnsManagerClient;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.dns.DNSGateway;
import com.callv2.dns.manager.domain.record.Ip;

@Component
@Profile({ "cloudflare" })
public class CloudflareDNSGateway implements DNSGateway {

    private final CloudflareDnsManagerClient cloudflareDnsManagerClient;
    private final DiscordWebhookNotifier discordWebhookNotifier;

    public CloudflareDNSGateway(
            final CloudflareDnsManagerClient cloudflareDnsManagerClient,
            final DiscordWebhookNotifier discordWebhookNotifier) {
        this.cloudflareDnsManagerClient = cloudflareDnsManagerClient;
        this.discordWebhookNotifier = discordWebhookNotifier;
    }

    @Override
    public void updateIP(DNS dns, Ip ip) {
        this.cloudflareDnsManagerClient.updateDns(dns.value(), dns.type().getValue(), ip.getValue());
        discordWebhookNotifier.sendMessage(
                String.format("🛠️ Registro DNS `%s` atualizado para o IP `%s`.", dns.value(), ip.getValue()));
    }

}
