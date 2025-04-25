package com.callv2.dns.manager.domain.record;

import java.time.Instant;
import java.util.Optional;
import java.util.Queue;

import com.callv2.dns.manager.domain.AggregateRoot;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventCarrier;
import com.callv2.dns.manager.domain.exception.IpTypeMismatchException;
import com.callv2.dns.manager.domain.validation.ValidationHandler;

public class Record extends AggregateRoot<RecordID> implements EventCarrier {

    private final RecordName name;
    private final RecordType type;
    private Ip ipId;
    private Instant updatedAt;

    private Queue<Event<?>> events;

    public Record(
            final RecordID id,
            final RecordName name,
            final RecordType type,
            final Ip ipId,
            final Instant updatedAt) {
        super(id);
        this.name = name;
        this.type = type;
        this.ipId = ipId;
        this.updatedAt = updatedAt;
    }

    public static Record of(
            final RecordName recordName,
            final RecordType recordType,
            final Ip ip,
            final Instant updatedAt) {
        return new Record(RecordID.from(recordName, recordType), recordName, recordType, ip, updatedAt);
    }

    public static Record create(
            final RecordID id,
            final RecordName recordName,
            final RecordType recordType,
            final Ip ip) {
        return new Record(id, recordName, recordType, ip, Instant.now());
    }

    @Override
    public void validate(final ValidationHandler handler) {
        // TODO implement validation
    }

    public RecordName getName() {
        return name;
    }

    public RecordType getType() {
        return type;
    }

    public Ip getIp() {
        return ipId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Record changeIp(final Ip ip) {

        if (ip.getType() != this.getType().getIpType())
            throw IpTypeMismatchException.with(this.getType().getIpType(), ip.getType());

        if (this.getIp().equals(ip))
            return this;

        this.ipId = ip;

        this.updatedAt = Instant.now();

        this.events.add(
                RecordChangedEvent.create(
                        "dns.manager",
                        RecordChangedEvent.Data.of(
                                this.getName().value(),
                                this.getType().getValue(),
                                this.getIp().getValue(),
                                ip.getValue())));

        return this;
    }

    @Override
    public Optional<Event<?>> dequeueEvent() {
        return Optional.ofNullable(this.events.poll());
    }

}
