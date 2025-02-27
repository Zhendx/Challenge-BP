package com.business.banking.client.application.output.port;

import reactor.core.publisher.Mono;

public interface PublisherEventPort {

    Mono<Boolean> sendClientEvent(String message);
}
