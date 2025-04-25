package com.callv2.dns.manager.domain.event;

@FunctionalInterface
public interface EventHandler<D> {

    void handle(Event<D> event);

}
