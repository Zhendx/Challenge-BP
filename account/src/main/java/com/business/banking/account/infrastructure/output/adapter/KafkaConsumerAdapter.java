package com.business.banking.account.infrastructure.output.adapter;

import com.business.banking.account.application.input.port.KafkaConsumerPort;
import com.business.banking.account.application.output.port.AccountRepositoryPort;
import com.business.banking.account.domain.Account;
import com.business.banking.account.domain.Client;
import com.business.banking.account.domain.Type;
import com.business.banking.account.infrastructure.util.GenerateUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerAdapter implements KafkaConsumerPort {
    private final AccountRepositoryPort accountRepositoryPort;

    @Bean
    @Override
    public Consumer<Message<String>> consume() {
        return this::processMessage;
    }

    public void processMessage(Message<String> message) {
        String messageString = message.getPayload();
        log.info("Receiving from temp_tpc_client: {} ", new Gson().toJson(messageString));
        Client client = new Gson().fromJson(messageString, Client.class);
        this.accountRepositoryPort.postAccount(createAccount(client.getClientId()))
                .subscribe();
    }

    private Account createAccount(String id) {
        return Account.builder()
                .clientId(id)
                .balance(BigDecimal.valueOf(0))
                .number(GenerateUtils.generator())
                .status(Boolean.TRUE)
                .type(Type.builder().code("AHO").description("AHORRO").build()).build();
    }
}
