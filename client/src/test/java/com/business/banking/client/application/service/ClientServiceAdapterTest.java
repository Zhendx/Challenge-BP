package com.business.banking.client.application.service;

import com.business.banking.client.infrastructure.output.mapper.ClientEntityMapper;
import com.business.banking.client.infrastructure.output.repository.ClientReactiveRepository;
import com.business.banking.client.util.MockObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceAdapterTest {
    @InjectMocks
    private ClientServiceAdapter clientServiceAdapter;
    @Mock
    private ClientEntityMapper clientEntityMapper;
    @Mock
    private ClientReactiveRepository clientReactiveRepository;

    @Test
    void testGetClientsByFilterSuccess() {
        when(clientReactiveRepository.findAll())
                .thenReturn(Flux.just(MockObjects.buildClientEntity()));
        when(clientEntityMapper.toClient(any()))
                .thenReturn(MockObjects.buildClient());

        StepVerifier.create(clientServiceAdapter.getClientByFilter())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetClientByIdSuccess() {
        when(clientReactiveRepository.findById(anyLong()))
                .thenReturn(Mono.just(MockObjects.buildClientEntity()));
        when(clientEntityMapper.toClient(any()))
                .thenReturn(MockObjects.buildClient());

        StepVerifier.create(clientServiceAdapter.getClientById(MockObjects.buildClient().getClientId()))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testPostClientSuccess() {
        when(clientEntityMapper.toClientEntity(any()))
                .thenReturn(MockObjects.buildClientEntity());
        when(clientReactiveRepository.save(any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(clientServiceAdapter.postClient(MockObjects.buildClient()))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void testPutClientSuccess() {
        when(clientReactiveRepository.findById(anyLong()))
                .thenReturn(Mono.just(MockObjects.buildClientEntity()));
        when(clientEntityMapper.toClient(any()))
                .thenReturn(MockObjects.buildClient());
        when(clientReactiveRepository.save(any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(clientServiceAdapter.putClient(MockObjects.buildClient().getClientId(), MockObjects.buildPutClientRequest()))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void testDeleteClientSuccess() {
        when(clientReactiveRepository.findById(anyLong()))
                .thenReturn(Mono.just(MockObjects.buildClientEntity()));
        when(clientReactiveRepository.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(clientServiceAdapter.deleteClient(MockObjects.buildClient().getClientId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}