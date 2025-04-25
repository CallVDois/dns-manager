package com.callv2.dns.manager.adapter.record.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordID;

public class InMemoryDnsRecordRepository {

    private final Map<DnsRecordID, DnsRecord> records = new HashMap<>();

    public void save(final DnsRecord record) {
        records.put(record.getId(), record);
    }

    public Optional<DnsRecord> findById(final DnsRecordID id) {
        return Optional.ofNullable(records.get(id));
    }

}
