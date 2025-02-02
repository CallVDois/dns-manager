package com.callv2.dns.manager.domain.ip;

@FunctionalInterface
public interface PublicIPRetriever {

    IP retrieve(Type type);

}
