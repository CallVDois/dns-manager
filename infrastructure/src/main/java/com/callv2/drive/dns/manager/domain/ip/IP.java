package com.callv2.drive.dns.manager.domain.ip;

public record IP(String value, Type type) {

    public enum Type {
        IPV4,
        IPV6;
    }

}
