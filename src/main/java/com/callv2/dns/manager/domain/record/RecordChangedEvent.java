package com.callv2.dns.manager.domain.record;

import java.time.Instant;
import java.util.UUID;

import com.callv2.dns.manager.domain.event.Event;

public record RecordChangedEvent(
        String id,
        String source,
        RecordChangedEvent.Data data,
        Instant generatedAt) implements Event<RecordChangedEvent.Data> {

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

    public static RecordChangedEvent create(final String source, final RecordChangedEvent.Data data) {
        return new RecordChangedEvent(UUID.randomUUID().toString(), source, data, Instant.now());
    }

}
