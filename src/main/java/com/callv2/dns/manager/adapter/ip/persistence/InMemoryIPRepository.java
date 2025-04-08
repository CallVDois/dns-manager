package com.callv2.dns.manager.adapter.ip.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.callv2.dns.manager.domain.ip.IP;

@Repository
public final class InMemoryIPRepository {

    private static IP ipv4;
    private static IP ipv6;

    public IP updateIP(final IP ip) {

        if (IP.Type.IPV4.equals(ip.type()))
            return InMemoryIPRepository.ipv4 = ip;

        if (IP.Type.IPV6.equals(ip.type()))
            return InMemoryIPRepository.ipv6 = ip;

        throw new RuntimeException("Invalid IP Type");
    }

    public Optional<IP> findIP(final IP.Type type) {

        if (IP.Type.IPV4.equals(type))
            return Optional.ofNullable(InMemoryIPRepository.ipv4);

        if (IP.Type.IPV6.equals(type))
            return Optional.ofNullable(InMemoryIPRepository.ipv6);

        throw new RuntimeException("Invalid IP Type");
    }

}
