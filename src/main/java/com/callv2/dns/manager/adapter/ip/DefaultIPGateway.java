package com.callv2.dns.manager.adapter.ip;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.ip.persistence.InMemoryIPRepository;
import com.callv2.dns.manager.adapter.ipify.IpifyPublicIPRetriever;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

@Component
public class DefaultIPGateway implements IpGateway {

    private final IpifyPublicIPRetriever ipRetriever;
    private final InMemoryIPRepository ipRepository;

    public DefaultIPGateway(
            final IpifyPublicIPRetriever ipRetriever,
            final InMemoryIPRepository ipRepository) {
        this.ipRetriever = ipRetriever;
        this.ipRepository = ipRepository;
    }

    @Override
    public Ip updateCurrentPublicIp(final Ip ip) {
        return this.ipRepository.updateIP(ip);
    }

    @Override
    public Optional<Ip> currentPublicIp(final IpType type) {
        return this.ipRepository.findIP(type);
    }

    @Override
    public Ip discoveryPublicIp(final IpType type) {

        if (type == null)
            throw new IllegalArgumentException();

        if (IpType.IPV4.equals(type))
            return Ip.fromIpv4(ipRetriever.retrievePublicIPV4().ip());

        if (IpType.IPV6.equals(type))
            return Ip.fromIpv4(ipRetriever.retrievePublicIPV6().ip());

        throw new IllegalArgumentException();
    }

}