package com.business.banking.account.infrastructure.output.adapter;

import com.business.banking.account.application.output.port.SpClientPort;
import com.business.banking.account.domain.Client;
import com.business.banking.account.infrastructure.output.adapter.mapper.ClientMapper;
import com.business.banking.account.infrastructure.output.adapter.rest.spClient.client.ClientApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpClientAdapter implements SpClientPort {
    private final ClientApi clientApi;
    private final ClientMapper clientMapper;

    @Override
    public Mono<Client> getClientById(String clientId) {
        log.info("|---> Starting to get client by id");
        return clientApi.getClientById(clientId)
                .map(clientMapper::toGetClientByIdResponse)
                .doOnError(error -> log.error("<---| Error to get client by id! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to get client by id!"));
    }
}
