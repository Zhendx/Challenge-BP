package com.business.banking.client.application.service;

import com.business.banking.client.application.input.port.ClientServicePort;
import com.business.banking.client.domain.Client;
import com.business.banking.client.domain.PatchClientRequest;
import com.business.banking.client.domain.PutClientRequest;
import com.business.banking.client.infrastructure.exception.AppException;
import com.business.banking.client.infrastructure.exception.custom.CustomError;
import com.business.banking.client.infrastructure.output.mapper.ClientEntityMapper;
import com.business.banking.client.infrastructure.output.repository.ClientReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceAdapter implements ClientServicePort {

    private final ClientReactiveRepository clientReactiveRepository;
    private final ClientEntityMapper clientEntityMapper;

    @Override
    public Mono<Void> deleteClient(String clientId) {
        log.info("|--> Starting to delete client");
        return clientReactiveRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .flatMap(client -> clientReactiveRepository.deleteById(client.getClientId())
                        .doOnError(error -> log.error("<--| Error to delete client! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to delete client!")))
                .then();
    }

    @Override
    public Flux<Client> getClientByFilter() {
        log.info("|--> Starting to get clients by filter");
        return clientReactiveRepository.findAll()
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(clientEntityMapper::toClient)
                .doOnError(error -> log.error("<--| Error to get clients by filter! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<--| Success to get clients by filter!"));
    }

    @Override
    public Mono<Client> getClientById(String clientId) {
        log.info("|--> Starting to get client by id");
        return clientReactiveRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(clientEntityMapper::toClient)
                .doOnError(error -> log.error("<--| Error to get client by id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to get client by id!"));
    }

    @Override
    public Mono<Void> patchClient(String clientId, PatchClientRequest patchClientRequest) {
        log.info("|--> Starting to patch client");
        return clientReactiveRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(clientEntityMapper::toClient)
                .flatMap(client -> {
                    client.setPassword(patchClientRequest.getPassword());
                    return clientReactiveRepository.save(clientEntityMapper.toClientEntity(client))
                            .doOnError(error -> log.error("<--| Error to patch client! -> {}", error.getMessage()))
                            .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                            .doOnNext(success -> log.info("<--| Success to patch client!"));
                })
                .then();
    }

    @Override
    public Mono<Void> postClient(Client postClientRequest) {
        log.info("|--> Starting to post client");
        return clientReactiveRepository.save(clientEntityMapper.toClientEntity(postClientRequest))
                .doOnError(error -> log.error("<--| Error to post client! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to post client!"))
                .then();
    }

    @Override
    public Mono<Void> putClient(String clientId, PutClientRequest putClientRequest) {
        log.info("|--> Starting to put client");
        return clientReactiveRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(clientEntityMapper::toClient)
                .map(client -> updateClient(client, putClientRequest))
                .flatMap(client -> clientReactiveRepository.save(clientEntityMapper.toClientEntityPut(client))
                        .doOnError(error -> log.error("<--| Error to put client! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to put client!")))
                .then();
    }

    private Client updateClient(Client client, PutClientRequest putClientRequest){
        client.setStatus(putClientRequest.getStatus());
        client.setPerson(putClientRequest.getPerson());
        return client;
    }
}
