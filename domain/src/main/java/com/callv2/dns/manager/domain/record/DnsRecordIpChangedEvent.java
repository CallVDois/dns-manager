package com.callv2.dns.manager.domain.record;

import java.time.Instant;
import java.util.UUID;

import com.callv2.dns.manager.domain.event.Event;

public record DnsRecordIpChangedEvent(
        String id,
        String source,
        DnsRecordIpChangedEvent.Data data,
        Instant generatedAt) implements Event<DnsRecordIpChangedEvent.Data> {

    private static final String NAME = "record.ip.changed";

    @Override
    public String name() {
        return NAME;
    }

    public record Data(
            String dns,
            String type,
            String oldIP,
            String newIP) {

        public static Data of(final String dns, final String type, final String oldIP, final String newIP) {
            return new Data(dns, type, oldIP, newIP);
        }

    }

    public static DnsRecordIpChangedEvent create(final String source, final DnsRecordIpChangedEvent.Data data) {
        return new DnsRecordIpChangedEvent(UUID.randomUUID().toString(), source, data, Instant.now());
    }

}
