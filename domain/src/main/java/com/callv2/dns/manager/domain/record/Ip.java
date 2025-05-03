package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.Entity;
import com.callv2.dns.manager.domain.validation.ValidationHandler;

public class Ip extends Entity<IpID> {

    private final String value;
    private final IpType type;

    private Ip(
            final IpID id,
            final String value,
            final IpType type) {
        super(id);
        this.value = value;
        this.type = type;
    }

    public static Ip localhostIpv6() {
        return fromIpv6("::1");
    }

    public static Ip localhostIpv4() {
        return fromIpv4("127.0.0.1");
    }

    public static Ip fromIpv6(final String value) {
        return new Ip(IpID.from(value), value, IpType.IPV6);
    }

    public static Ip fromIpv4(final String value) {
        return new Ip(IpID.from(value), value, IpType.IPV4);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new IpValidator(handler, this).validate();
    }

    public String getValue() {
        return value;
    }

    public IpType getType() {
        return type;
    }

}
