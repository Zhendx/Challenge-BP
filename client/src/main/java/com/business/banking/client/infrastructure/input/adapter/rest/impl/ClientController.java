package com.business.banking.client.infrastructure.input.adapter.rest.impl;

import com.business.banking.client.application.input.port.ClientServicePort;
import com.business.banking.client.infrastructure.input.adapter.rest.SupportApi;
import com.business.banking.client.infrastructure.input.adapter.rest.mapper.ClientMapper;
import com.business.banking.client.infrastructure.input.adapter.rest.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController implements SupportApi {
    private final ClientServicePort clientServicePort;
    private final ClientMapper clientMapper;

    @Override
    public Mono<ResponseEntity<Void>> deleteClient(String clientId, ServerWebExchange exchange) {
        log.info("|-> Start delete client");
        return clientServicePort.deleteClient(clientId)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete client finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Flux<Client>>> getClientByFilter(ServerWebExchange exchange) {
        log.info("|-> Start get client by filter");
        return clientServicePort.getClientByFilter()
                .map(clientMapper::toClient)
                .collectList()
                .map(response -> ResponseEntity.ok().body(Flux.fromIterable(response)))
                .doOnError(e -> log.error("<-| Get client by filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get client by filter finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<GetClientByIdResponse>> getClientById(String clientId, ServerWebExchange exchange) {
        log.info("|-> Start get client by id");
        return clientServicePort.getClientById(clientId)
                .map(clientMapper::toGetClientByIdResponse)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get client by id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get client by id finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchClient(String clientId, Mono<PatchClientRequest> patchClientRequest, ServerWebExchange exchange) {
        log.info("|-> Start patch client");
        return patchClientRequest
                .flatMap(patchClient -> clientServicePort.patchClient(clientId, clientMapper.toPatchClientRequest(patchClient)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Patch client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Patch client finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> postClient(Mono<PostClientRequest> postClientRequest, ServerWebExchange exchange) {
        log.info("|-> Start post client");
        return postClientRequest
                .flatMap(postClient -> clientServicePort.postClient(clientMapper.toClient(postClient)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post client finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> putClient(String clientId, Mono<PutClientRequest> putClientRequest, ServerWebExchange exchange) {
        log.info("|-> Start put client");
        return putClientRequest
                .flatMap(putClient -> clientServicePort.putClient(clientId, clientMapper.toPutClientRequest(putClient)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put client finished successfully"));
    }
}
