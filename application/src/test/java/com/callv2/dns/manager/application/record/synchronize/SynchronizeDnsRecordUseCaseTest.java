package com.callv2.dns.manager.application.record.synchronize;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.callv2.dns.manager.domain.event.EventCarrier;
import com.callv2.dns.manager.domain.event.EventDispatcher;
import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.DnsRecordID;
import com.callv2.dns.manager.domain.record.DnsRecordName;
import com.callv2.dns.manager.domain.record.DnsRecordType;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

@ExtendWith(MockitoExtension.class)
public class SynchronizeDnsRecordUseCaseTest {

    @InjectMocks
    SynchronizeDnsRecordUseCase useCase;

    @Mock
    IpGateway ipGateway;

    @Mock
    DnsRecordGateway dnsRecordGateway;

    @Mock
    EventDispatcher eventDispatcher;

    @Test
    void givenAValidInput_whenPublicIpNotChange_thenShouldNotPerformChangeIp() {

        final var expectedDns = "example.com";
        final var expectedDnsRecordName = DnsRecordName.of(expectedDns);
        final var expectedDnsRecordId = DnsRecordID.from(expectedDnsRecordName, DnsRecordType.A);
        final var expectedDnsRecordType = DnsRecordType.A;
        final var expectedIpType = IpType.IPV4;
        final var expectedIp = Ip.fromIpv4("179.0.0.2");

        final var expectedDnsRecord = DnsRecord.of(expectedDnsRecordName, expectedDnsRecordType, expectedIp);

        when(ipGateway.findPublicIp(eq(expectedIpType)))
                .thenReturn(expectedIp);

        when(dnsRecordGateway.findById(expectedDnsRecordId))
                .thenReturn(Optional.of(expectedDnsRecord));

        final var input = new SynchronizeDnsRecordInput(expectedDns, expectedDnsRecordType);
        assertDoesNotThrow(() -> useCase.execute(input));

        assertTrue(expectedDnsRecord.dequeueEvent().isEmpty());
        assertTrue(expectedDnsRecord.getIp().equals(expectedIp));

        verify(eventDispatcher, times(0)).notify(any(EventCarrier.class));
        verify(dnsRecordGateway, times(0)).update(any());

    }

    @Test
    void givenAValidInput_whenPublicIpChange_thenShouldPerformChangeIp() {

        final var expectedDns = "example.com";
        final var expectedDnsRecordName = DnsRecordName.of(expectedDns);
        final var expectedDnsRecordId = DnsRecordID.from(expectedDnsRecordName, DnsRecordType.A);
        final var expectedDnsRecordType = DnsRecordType.A;
        final var expectedIpType = IpType.IPV4;
        final var expectedIp = Ip.fromIpv4("179.0.0.2");

        final var expectedDnsRecord = DnsRecord.of(expectedDnsRecordName, expectedDnsRecordType, expectedIp);

        when(ipGateway.findPublicIp(eq(expectedIpType)))
                .thenReturn(expectedIp);

        when(dnsRecordGateway.findById(expectedDnsRecordId))
                .thenReturn(Optional.empty());

        final var input = new SynchronizeDnsRecordInput(expectedDns, expectedDnsRecordType);
        assertDoesNotThrow(() -> useCase.execute(input));

        assertTrue(expectedDnsRecord.dequeueEvent().isEmpty());
        assertTrue(expectedDnsRecord.getIp().equals(expectedIp));

        verify(eventDispatcher, times(1)).notify(any(EventCarrier.class));
        verify(dnsRecordGateway, times(1)).update(eq(expectedDnsRecord));
        verify(dnsRecordGateway, times(1)).update(any());

    }

    @Test
    void givenAValidInputWithWrongRecordType_whenPublicIpChange_thenShouldNotPerformChangeIpAndNotifyErrors() {

        final var expectedDns = "example.com";
        final var expectedDnsRecordName = DnsRecordName.of(expectedDns);
        final var expectedDnsRecordId = DnsRecordID.from(expectedDnsRecordName, DnsRecordType.AAAA);
        final var expectedDnsRecordType = DnsRecordType.AAAA;
        final var expectedIpType = IpType.IPV6;
        final var expectedIp = Ip.fromIpv6("::2");

        final var expectedDnsRecord = DnsRecord.of(expectedDnsRecordName, expectedDnsRecordType, expectedIp);

        when(ipGateway.findPublicIp(eq(expectedIpType)))
                .thenReturn(expectedIp);

        when(dnsRecordGateway.findById(eq(expectedDnsRecordId)))
                .thenReturn(Optional.empty());

        final var input = new SynchronizeDnsRecordInput(expectedDns, expectedDnsRecordType);
        assertDoesNotThrow(() -> useCase.execute(input));

        assertTrue(expectedDnsRecord.dequeueEvent().isEmpty());
        assertTrue(expectedDnsRecord.getIp().equals(expectedIp));

        verify(eventDispatcher, times(1)).notify(any(EventCarrier.class));
        verify(dnsRecordGateway, times(1)).update(eq(expectedDnsRecord));
        verify(dnsRecordGateway, times(1)).update(any());

    }

}
