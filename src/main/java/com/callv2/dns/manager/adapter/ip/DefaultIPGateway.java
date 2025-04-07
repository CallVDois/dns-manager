package com.callv2.dns.manager.adapter.ip;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.ip.persistence.InMemoryIPRepository;
import com.callv2.dns.manager.adapter.ipify.IpifyPublicIPRetriever;
import com.callv2.dns.manager.domain.ip.IP;
import com.callv2.dns.manager.domain.ip.IPGateway;
import com.callv2.dns.manager.domain.ip.IP.Type;

@Component
public class DefaultIPGateway implements IPGateway {

    private final IpifyPublicIPRetriever ipRetriever;
    private final InMemoryIPRepository ipRepository;

    public DefaultIPGateway(
            final IpifyPublicIPRetriever ipRetriever,
            final InMemoryIPRepository ipRepository) {
        this.ipRetriever = ipRetriever;
        this.ipRepository = ipRepository;
    }

    @Override
    public IP updateCurrentIP(final IP ip) {
        return this.ipRepository.updateIP(ip);
    }

    @Override
    public Optional<IP> currentPublicIP(final Type type) {
        return this.ipRepository.findIP(type);
    }

    @Override
    public IP discoveryPublicIP(final IP.Type type) {

        if (type == null)
            throw new IllegalArgumentException();

        if (IP.Type.IPV4.equals(type))
            return new IP(ipRetriever.retrievePublicIPV4().ip(), IP.Type.IPV4);

        if (IP.Type.IPV6.equals(type))
            return new IP(ipRetriever.retrievePublicIPV6().ip(), IP.Type.IPV6);

        throw new IllegalArgumentException();
    }

}