package com.callv2.dns.manager.infrastructure.record;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.DnsRecordID;
import com.callv2.dns.manager.infrastructure.external.cloudflare.CloudflareDnsManagerClient;
import com.callv2.dns.manager.infrastructure.record.persistence.InMemoryDnsRecordRepository;

@Profile({ "cloudflare" })
@Component
public class DefaultDnsRecordGateway implements DnsRecordGateway {

    private final InMemoryDnsRecordRepository inMemoryDnsRecordRepository;
    private final CloudflareDnsManagerClient cloudflareDnsManagerClient;

    public DefaultDnsRecordGateway(
            final InMemoryDnsRecordRepository inMemoryDnsRecordRepository,
            final CloudflareDnsManagerClient cloudflareDnsManagerClient) {
        this.inMemoryDnsRecordRepository = inMemoryDnsRecordRepository;
        this.cloudflareDnsManagerClient = cloudflareDnsManagerClient;
    }

    @Override
    public Optional<DnsRecord> findById(DnsRecordID id) {
        return inMemoryDnsRecordRepository.findById(id);
    }

    @Override
    public void update(final com.callv2.dns.manager.domain.record.DnsRecord record) {

        this.cloudflareDnsManagerClient.updateDns(
                record.getName().value(),
                record.getType().getValue(),
                record.getIp().getValue());

        this.inMemoryDnsRecordRepository.save(record);
    }

}
