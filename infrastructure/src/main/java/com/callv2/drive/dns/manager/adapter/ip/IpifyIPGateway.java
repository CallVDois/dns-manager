package com.callv2.drive.dns.manager.adapter.ip;

import com.callv2.drive.dns.manager.adapter.ipify.IpifyPublicIPRetriever;
import com.callv2.drive.dns.manager.domain.ip.IP;
import com.callv2.drive.dns.manager.domain.ip.IPGateway;

public class IpifyIPGateway implements IPGateway {

    private final IpifyPublicIPRetriever ipRetriever;

    public IpifyIPGateway(final IpifyPublicIPRetriever ipRetriever) {
        this.ipRetriever = ipRetriever;
    }

    @Override
    public IP discoveryPublicIP(final IP.Type type) {

        if (type == null)
            throw new IllegalArgumentException();

        if (IP.Type.IPV4.equals(type))
            return ipv4();

        if (IP.Type.IPV6.equals(type))
            return ipv6();

        throw new IllegalArgumentException();
    }

    private IP ipv4() {
        return new IP(ipRetriever.retrievePublicIPV4().ip(), IP.Type.IPV4);
    }

    private IP ipv6() {
        return new IP(ipRetriever.retrievePublicIPV6().ip(), IP.Type.IPV6);
    }

}