package com.callv2.dns.manager.adapter.ip;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.external.ipify.IpifyPublicIPRetriever;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

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

        if (IpType.IPV4.equals(type))
            return Ip.fromIpv4(ipRetriever.retrievePublicIPV4().ip());

        if (IpType.IPV6.equals(type))
            return Ip.fromIpv4(ipRetriever.retrievePublicIPV6().ip());

        throw new IllegalArgumentException();
    }

}