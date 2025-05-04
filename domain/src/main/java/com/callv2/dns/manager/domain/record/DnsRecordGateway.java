package com.callv2.dns.manager.domain.record;

import java.util.Optional;

public interface DnsRecordGateway {

    Optional<DnsRecord> findById(DnsRecordID id);

    void update(DnsRecord record);

}
