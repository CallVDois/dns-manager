package com.callv2.dns.manager.domain.event;

import java.time.Instant;
import java.util.UUID;

public record DnsChangedEvent(
        String id,
        String source,
        DnsChangedEvent.Data data,
        Instant generatedAt) implements Event<DnsChangedEvent.Data> {

    private static final String NAME = "dns.changed";

    @Override
    public String name() {
        return NAME;
    }

    public record Data(
            String dns,
            String type,
            String oldIP,
            String newIP) {

    }

    public static DnsChangedEvent create(final String source, final DnsChangedEvent.Data data) {
        return new DnsChangedEvent(UUID.randomUUID().toString(), source, data, Instant.now());
    }

}
