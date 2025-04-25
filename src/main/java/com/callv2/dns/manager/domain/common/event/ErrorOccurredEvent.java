package com.callv2.dns.manager.domain.common.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.exception.DomainException;

public record ErrorOccurredEvent(
        String id,
        String source,
        ErrorOccurredEvent.Data data,
        Instant generatedAt) implements Event<ErrorOccurredEvent.Data> {

    private static final String NAME = "domain.error.occurred";

    public static ErrorOccurredEvent create(final String source, final ErrorOccurredEvent.Data data) {
        return new ErrorOccurredEvent(
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

        public static Data of(final DomainException cause) {
            return new Data(cause.getMessage(),
                    cause.getErrors().stream().map(DomainException.Error::message).toList());
        }
    }

}
