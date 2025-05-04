package com.callv2.dns.manager.domain.common.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.exception.DomainException;

public record DomainErrorOccurredEvent(
        String id,
        String source,
        DomainErrorOccurredEvent.Data data,
        Instant generatedAt) implements Event<DomainErrorOccurredEvent.Data> {

    private static final String NAME = "domain.error.occurred";

    public static DomainErrorOccurredEvent create(final String source, final DomainErrorOccurredEvent.Data data) {
        return new DomainErrorOccurredEvent(
                UUID.randomUUID().toString(),
                source,
                data,
                Instant.now());
    }

    @Override
    public String name() {
        return NAME;
    }

    public record Data(String message, List<String> errors) {

        public static Data of(final String message, final List<DomainException.Error> errors) {
            return new Data(message, errors.stream().map(DomainException.Error::message).toList());
        }

        public static Data of(final DomainException cause) {
            return new Data(cause.getMessage(),
                    cause.getErrors().stream().map(DomainException.Error::message).toList());
        }
    }

}
