package com.callv2.dns.manager.domain.record;

import java.time.Instant;
import java.util.UUID;

import com.callv2.dns.manager.domain.event.Event;

public record DnsRecordChangedEvent(
        String id,
        String source,
        DnsRecordChangedEvent.Data data,
        Instant generatedAt) implements Event<DnsRecordChangedEvent.Data> {

    private static final String NAME = "record.changed";

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

    public static DnsRecordChangedEvent create(final String source, final DnsRecordChangedEvent.Data data) {
        return new DnsRecordChangedEvent(UUID.randomUUID().toString(), source, data, Instant.now());
    }

}
