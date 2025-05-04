package com.callv2.dns.manager.infrastructure.record.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordID;

@Repository
public class InMemoryDnsRecordRepository {

    private final Map<DnsRecordID, DnsRecord> records = new HashMap<>();

    public void save(final DnsRecord record) {
        records.put(record.getId(), record);
    }

    public Optional<DnsRecord> findById(final DnsRecordID id) {
        return Optional.ofNullable(records.get(id));
    }

}
