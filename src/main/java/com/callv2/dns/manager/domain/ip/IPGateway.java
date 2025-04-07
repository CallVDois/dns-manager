package com.callv2.dns.manager.domain.ip;

import java.util.Optional;

public interface IPGateway {

    IP updateCurrentIP(IP ip);

    Optional<IP> currentPublicIP(IP.Type type);

    IP discoveryPublicIP(IP.Type type);

}
