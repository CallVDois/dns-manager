package com.callv2.dns.manager.domain.record;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.callv2.dns.manager.domain.common.event.DomainErrorOccurredEvent;
import com.callv2.dns.manager.domain.exception.IpTypeMismatchException;

class DnsRecordTest {

    @Test
    void givenAValidParamsForAAAARecord_whenCallsCreate_shouldInstantiateADnsRecord() {

        final var expectedIp = Ip.localhostIpv6();
        final var expectedName = DnsRecordName.of("example.com");
        final var expectedType = DnsRecordType.AAAA;
        final var expectedDnsRecordId = DnsRecordID.from(expectedName, expectedType);

        final var actualDnsRecord = DnsRecord.create(
                expectedName,
                expectedType);

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

        assertEquals(expectedIp, actualDnsRecord.getIp());
        assertEquals(expectedName, actualDnsRecord.getName());
        assertEquals(expectedType, actualDnsRecord.getType());
        assertEquals(expectedDnsRecordId, actualDnsRecord.getId());
    }

    @Test
    void givenAValidParamsForARecord_whenCallsCreate_shouldInstantiateADnsRecord() {

        final var expectedIp = Ip.localhostIpv4();
        final var expectedName = DnsRecordName.of("example.com");
        final var expectedType = DnsRecordType.A;
        final var expectedDnsRecordId = DnsRecordID.from(expectedName, expectedType);

        final var actualDnsRecord = DnsRecord.create(
                expectedName,
                expectedType);

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

        assertEquals(expectedIp, actualDnsRecord.getIp());
        assertEquals(expectedName, actualDnsRecord.getName());
        assertEquals(expectedType, actualDnsRecord.getType());
        assertEquals(expectedDnsRecordId, actualDnsRecord.getId());
    }

    @Test
    void givenAnValidIp_whenCallshangeIp_thenReturnUpdatedDnsRecord() {

        final var oldIp = Ip.fromIpv4("127.0.0.1");

        final var expectedIp = Ip.fromIpv4("179.0.0.220");
        final var expectedName = DnsRecordName.of("example.com");
        final var expectedType = DnsRecordType.A;
        final var expectedDnsRecordId = DnsRecordID.from(expectedName, expectedType);

        final var actualDnsRecord = DnsRecord.create(
                expectedName,
                expectedType);

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

        assertDoesNotThrow(() -> actualDnsRecord.changeIp(expectedIp));

        assertEquals(expectedIp, actualDnsRecord.getIp());
        assertEquals(expectedName, actualDnsRecord.getName());
        assertEquals(expectedType, actualDnsRecord.getType());
        assertEquals(expectedDnsRecordId, actualDnsRecord.getId());
        assertNotEquals(oldIp, expectedIp);
        assertTrue(actualDnsRecord.dequeueEvent().isPresent());

    }

    @Test
    void givenAnValidSameIpV4_whenCallshangeIp_thenReturnDontPerformUpdateDnsRecord() {

        final var expectedIp = Ip.fromIpv4("179.0.0.220");
        final var expectedName = DnsRecordName.of("example.com");
        final var expectedType = DnsRecordType.A;
        final var expectedDnsRecordId = DnsRecordID.from(expectedName, expectedType);

        final var actualIp = Ip.fromIpv4("179.0.0.220");
        final var actualDnsRecord = DnsRecord.of(
                expectedName,
                expectedType,
                expectedIp);

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

        assertDoesNotThrow(() -> actualDnsRecord.changeIp(expectedIp));

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

        assertEquals(expectedIp, actualDnsRecord.getIp());
        assertEquals(expectedName, actualDnsRecord.getName());
        assertEquals(expectedType, actualDnsRecord.getType());
        assertEquals(expectedDnsRecordId, actualDnsRecord.getId());
        assertEquals(actualIp, expectedIp);

    }

    @Test
    void givenADiffrentIpType_whenCallsChangeIp_shouldThrowIpTypeMismatchException() {

        final var expectedEventType = DomainErrorOccurredEvent.class;

        final var expectedIp = Ip.localhostIpv6();
        final var expectedName = DnsRecordName.of("example.com");
        final var expectedType = DnsRecordType.AAAA;

        final var actualDnsRecord = DnsRecord.of(
                expectedName,
                expectedType,
                expectedIp);

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());
        assertThrows(IpTypeMismatchException.class, () -> actualDnsRecord.changeIp(Ip.localhostIpv4()));

        final var actualEvent = assertDoesNotThrow(() -> actualDnsRecord.dequeueEvent().orElseThrow());
        assertEquals(expectedEventType, actualEvent.getClass());

        assertTrue(actualDnsRecord.dequeueEvent().isEmpty());

    }

}