package com.callv2.dns.manager.domain.record;

import java.util.Optional;

public interface IpGateway {

    Ip updateCurrentPublicIp(Ip ip);

    Optional<Ip> currentPublicIp(IpType type);

    Ip discoveryPublicIp(IpType type);

}
