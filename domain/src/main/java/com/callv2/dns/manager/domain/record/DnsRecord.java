package com.callv2.dns.manager.domain.record;

import java.util.Optional;
import java.util.Queue;

import com.callv2.dns.manager.domain.AggregateRoot;
import com.callv2.dns.manager.domain.common.event.DomainErrorOccurredEvent;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.event.EventCarrier;
import com.callv2.dns.manager.domain.exception.IpTypeMismatchException;
import com.callv2.dns.manager.domain.validation.ValidationHandler;

public class DnsRecord extends AggregateRoot<DnsRecordID> implements EventCarrier {

    private final DnsRecordName name;
    private final DnsRecordType type;
    private Ip ip;

    private Queue<Event<?>> events;

    private DnsRecord(
            final DnsRecordID id,
            final DnsRecordName name,
            final DnsRecordType type,
            final Ip ipId) {
        super(id);
        this.name = name;
        this.type = type;
        this.ip = ipId;

        this.events = new java.util.LinkedList<>();
    }

    public static DnsRecord of(
            final DnsRecordName recordName,
            final DnsRecordType recordType,
            final Ip ip) {
        return new DnsRecord(
                DnsRecordID.from(recordName, recordType),
                recordName,
                recordType,
                ip);
    }

    public static DnsRecord create(
            final DnsRecordName recordName,
            final DnsRecordType recordType) {
        return new DnsRecord(
                DnsRecordID.from(recordName, recordType),
                recordName,
                recordType,
                IpType.IPV4.equals(recordType.getIpType())
                        ? Ip.localhostIpv4()
                        : Ip.localhostIpv6());
    }

    @Override
    public void validate(final ValidationHandler handler) {
        // TODO implement validation
    }

    @Override
    public Optional<Event<?>> dequeueEvent() {
        return Optional.ofNullable(this.events.poll());
    }

    public DnsRecordName getName() {
        return name;
    }

    public DnsRecordType getType() {
        return type;
    }

    public Ip getIp() {
        return ip;
    }

    public DnsRecord changeIp(final Ip ip) {

        if (ip.getType() != this.getType().getIpType()) {
            final var e = IpTypeMismatchException.with(this.getType().getIpType(), ip.getType());
            addEvent(DomainErrorOccurredEvent.create(
                    "dns.manager",
                    DomainErrorOccurredEvent.Data.of("Registro DNS '%s'".formatted(this.getName().value()), e.getErrors())));
            throw e;
        }

        if (this.getIp().equals(ip))
            return this;

        this.ip = ip;

        addEvent(
                DnsRecordIpChangedEvent.create(
                        "dns.manager",
                        DnsRecordIpChangedEvent.Data.of(
                                this.getName().value(),
                                this.getType().getValue(),
                                this.getIp().getValue(),
                                ip.getValue())));

        return this;
    }

    private void addEvent(final Event<?> event) {
        this.events.add(event);
    }

}
