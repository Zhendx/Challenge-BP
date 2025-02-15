package com.business.banking.client.application.input.port;

import com.business.banking.client.domain.Client;
import com.business.banking.client.domain.PatchClientRequest;
import com.business.banking.client.domain.PutClientRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientServicePort {
    Mono<Void> deleteClient(String clientId);
    Flux<Client> getClientByFilter();
    Mono<Client> getClientById(String clientId);
    Mono<Void> patchClient(String clientId, PatchClientRequest patchClientRequest);
    Mono<Void> postClient(Client postClientRequest);
    Mono<Void> putClient(String clientId, PutClientRequest putClientRequest);
}
