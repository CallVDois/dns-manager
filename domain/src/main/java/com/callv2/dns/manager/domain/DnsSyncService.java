package com.callv2.dns.manager.domain;

import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.ip.IP;
import com.callv2.dns.manager.domain.ip.PublicIPRetriever;
import com.callv2.dns.manager.domain.ip.Type;

public abstract class DnsSyncService {

    private final DNS dns;
    private final PublicIPRetriever publicIPRetriever;
    private IP lastPublicIP;
    private final Type ipType;

    public DnsSyncService(
            final DNS dns,
            final PublicIPRetriever publicIPRetriever,
            final Type ipType) {
        this.dns = dns;
        this.publicIPRetriever = publicIPRetriever;
        this.ipType = ipType;
    }

    public void sync() {

        final IP publicIP = publicIPRetriever.retrieve(this.ipType);

        if (publicIP.equals(lastPublicIP))
            return;

        lastPublicIP = publicIP;
        updateDns(dns, publicIP);
    }

    protected void setLastPublicIP(IP lastPublicIP) {
        this.lastPublicIP = lastPublicIP;
    }

    protected abstract void updateDns(DNS dns, IP publicIP);

    public DNS getDns() {
        return dns;
    }

    public PublicIPRetriever getPublicIPRetriever() {
        return publicIPRetriever;
    }

    public IP getLastPublicIP() {
        return lastPublicIP;
    }

    public Type getIpType() {
        return ipType;
    }

}
