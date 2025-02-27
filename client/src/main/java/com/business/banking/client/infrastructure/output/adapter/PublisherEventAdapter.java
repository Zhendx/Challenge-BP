package com.business.banking.client.infrastructure.output.adapter;

import com.business.banking.client.application.output.port.PublisherEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherEventAdapter implements PublisherEventPort {

    private final StreamBridge streamBridge;

    @Override
    public Mono<Boolean> sendClientEvent(String message) {
        return Mono.just(streamBridge.send("producerClient-out-0", message))
                .doOnError(err -> log.error("Error sending data to topic: {}", err.getMessage()))
                .doOnSuccess(send -> log.info("Successfully sent client data to topic"));
    }
}
