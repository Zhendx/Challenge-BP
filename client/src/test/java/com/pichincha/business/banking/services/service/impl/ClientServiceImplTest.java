package com.pichincha.business.banking.services.service.impl;

import com.pichincha.business.banking.services.repository.impl.ClientRepository;
import com.pichincha.business.banking.services.util.MockObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testGetClientsByFilterSuccess() {
        when(clientRepository.findAll())
                .thenReturn(List.of(MockObjects.buildClient()));
        StepVerifier.create(clientService.getClientsByFilter())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetClientByIdSuccess() {
        when(clientRepository.findFirstByIdentification(any()))
                .thenReturn(MockObjects.buildClient());
        StepVerifier.create(clientService.getClientById(any()))
                .expectNextCount(1)
                .verifyComplete();
    }
/*
    @Test
    void testPostClientSuccess() {
        when(clientRepository.save(any()))
                .thenReturn(MockObjects.buildClient());
        StepVerifier.create(clientService.postClient(MockObjects.buildClientRequestDTO()))
                .expectNextCount(1)
                .verifyComplete();
    }*/

    @Test
    void testPutClientSuccess() {
        when(clientRepository.findFirstByIdentification(any()))
                .thenReturn(MockObjects.buildClient());
        when(clientRepository.save(any()))
                .thenReturn(MockObjects.buildClient());
        StepVerifier.create(clientService.putClient("ID123" , MockObjects.buildClientRequestDTO()))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testDeleteClientSuccess() {
        when(clientRepository.findFirstByIdentification(any()))
                .thenReturn(MockObjects.buildClient());
        StepVerifier.create(clientService.deleteClient("ID123"))
                .expectNextCount(0)
                .verifyComplete();
    }
}