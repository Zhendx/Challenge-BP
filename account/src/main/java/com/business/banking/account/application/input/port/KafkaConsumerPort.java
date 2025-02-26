package com.business.banking.account.application.input.port;

import org.springframework.messaging.Message;

import java.util.function.Consumer;

public interface KafkaConsumerPort {
    Consumer<Message<String>> consume();
}
