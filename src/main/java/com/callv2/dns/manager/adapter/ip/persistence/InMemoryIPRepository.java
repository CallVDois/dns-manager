package com.callv2.dns.manager.adapter.ip.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpType;

@Repository
public final class InMemoryIPRepository {

    private static Ip ipv4;
    private static Ip ipv6;

    public Ip updateIP(final Ip ip) {

        if (IpType.IPV4.equals(ip.getType()))
            return InMemoryIPRepository.ipv4 = ip;

        if (IpType.IPV6.equals(ip.getType()))
            return InMemoryIPRepository.ipv6 = ip;

        throw new RuntimeException("Invalid IP Type");
    }

    public Optional<Ip> findIP(final IpType type) {

        if (IpType.IPV4.equals(type))
            return Optional.ofNullable(InMemoryIPRepository.ipv4);

        if (IpType.IPV6.equals(type))
            return Optional.ofNullable(InMemoryIPRepository.ipv6);

        throw new RuntimeException("Invalid IP Type");
    }

}
