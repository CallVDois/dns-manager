package com.callv2.dns.manager.domain.record;

import java.time.Instant;
import java.util.Optional;
import java.util.Queue;

import com.callv2.dns.manager.domain.AggregateRoot;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventCarrier;
import com.callv2.dns.manager.domain.exception.IpTypeMismatchException;
import com.callv2.dns.manager.domain.validation.ValidationHandler;

public class DnsRecord extends AggregateRoot<DnsRecordID> implements EventCarrier {

    private final DnsRecordName name;
    private final DnsRecordType type;
    private Ip ipId;
    private Instant updatedAt;

    private Queue<Event<?>> events;

    public DnsRecord(
            final DnsRecordID id,
            final DnsRecordName name,
            final DnsRecordType type,
            final Ip ipId,
            final Instant updatedAt) {
        super(id);
        this.name = name;
        this.type = type;
        this.ipId = ipId;
        this.updatedAt = updatedAt;
    }

    public static DnsRecord of(
            final DnsRecordName recordName,
            final DnsRecordType recordType,
            final Ip ip,
            final Instant updatedAt) {
        return new DnsRecord(DnsRecordID.from(recordName, recordType), recordName, recordType, ip, updatedAt);
    }

    public static DnsRecord create(
            final DnsRecordID id,
            final DnsRecordName recordName,
            final DnsRecordType recordType,
            final Ip ip) {
        return new DnsRecord(id, recordName, recordType, ip, Instant.now());
    }

    @Override
    public void validate(final ValidationHandler handler) {
        // TODO implement validation
    }

    public DnsRecordName getName() {
        return name;
    }

    public DnsRecordType getType() {
        return type;
    }

    public Ip getIp() {
        return ipId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public DnsRecord changeIp(final Ip ip) {

        if (ip.getType() != this.getType().getIpType())
            throw IpTypeMismatchException.with(this.getType().getIpType(), ip.getType());

        if (this.getIp().equals(ip))
            return this;

        this.ipId = ip;

        this.updatedAt = Instant.now();

        this.events.add(
                DnsRecordChangedEvent.create(
                        "dns.manager",
                        DnsRecordChangedEvent.Data.of(
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
