package com.callv2.dns.manager.infrastructure.ip;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;
import com.callv2.dns.manager.infrastructure.external.ipify.IpifyPublicIPRetriever;

@Component
public class IpifyIpGateway implements IpGateway {

    private final IpifyPublicIPRetriever ipRetriever;

    public IpifyIpGateway(final IpifyPublicIPRetriever ipRetriever) {
        this.ipRetriever = ipRetriever;
    }

    @Override
    public Ip findPublicIp(final IpType type) {

        if (type == null)
            throw new IllegalArgumentException();

        final Ip ip;
        if (IpType.IPV4.equals(type))
            ip = Ip.fromIpv4(ipRetriever.retrievePublicIPV4().ip());
        else
            ip = Ip.fromIpv6(ipRetriever.retrievePublicIPV6().ip());

        return ip;
    }

}