package com.pichincha.business.banking.services.controller;

import com.pichincha.business.banking.services.service.ClientService;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Test
    void testGetClientByFilterSuccess() {
        when(clientService.getClientsByFilter())
                .thenReturn(Mono.just(Flux.just(new ClientResponseDTO())));
        StepVerifier.create(clientController.getClientByFilter())
                .assertNext(fluxResponseEntity -> assertThat(fluxResponseEntity.getStatusCode(), equalTo(HttpStatus.OK)))
                .verifyComplete();
        verify(clientService, times(1)).getClientsByFilter();
    }

    @Test
    void testGetClientByFilterError() {
        when(clientService.getClientsByFilter())
                .thenReturn(Mono.error(new RuntimeException("Error")));
        StepVerifier.create(clientController.getClientByFilter())
                .expectError(RuntimeException.class)
                .verify();
        verify(clientService, times(1)).getClientsByFilter();
    }

    @Test
    void testGetClientByIdSuccess() {
        when(clientService.getClientById(any()))
                .thenReturn(Mono.just(new ClientResponseDTO()));
        StepVerifier.create(clientController.getClientById(any()))
                .assertNext(fluxResponseEntity -> assertThat(fluxResponseEntity.getStatusCode(), equalTo(HttpStatus.OK)))
                .verifyComplete();
        verify(clientService, times(1)).getClientById(any());
    }

    @Test
    void testGetClientByIdError() {
        when(clientService.getClientById(any()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        StepVerifier.create(clientController.getClientById(any()))
                .expectError(RuntimeException.class)
                .verify();
        verify(clientService, times(1)).getClientById(any());
    }
    @Test
    void testPostClientSuccess() {
        when(clientService.postClient(any()))
                .thenReturn(Mono.just(new ClientResponseDTO()));
        StepVerifier.create(clientController.postClient(any()))
                .assertNext(fluxResponseEntity -> assertThat(fluxResponseEntity.getStatusCode(), equalTo(HttpStatus.OK)))
                .verifyComplete();
        verify(clientService, times(1)).postClient(any());
    }

    @Test
    void testPostClientError() {
        when(clientService.postClient(any()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        StepVerifier.create(clientController.postClient(any()))
                .expectError(RuntimeException.class)
                .verify();
        verify(clientService, times(1)).postClient(any());
    }

    @Test
    void testPutClientSuccess() {
        when(clientService.putClient(any(), any()))
                .thenReturn(Mono.just(new ClientResponseDTO()));
        StepVerifier.create(clientController.putClient(any(), any()))
                .assertNext(fluxResponseEntity -> assertThat(fluxResponseEntity.getStatusCode(), equalTo(HttpStatus.OK)))
                .verifyComplete();
        verify(clientService, times(1)).putClient(any(), any());
    }

    @Test
    void testPutClientError() {
        when(clientService.putClient(any(), any()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        StepVerifier.create(clientController.putClient(any(), any()))
                .expectError(RuntimeException.class)
                .verify();
        verify(clientService, times(1)).putClient(any(), any());
    }

    @Test
    void testDeleteClientSuccess() {
        when(clientService.deleteClient(any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientController.deleteClient(any()))
                .expectNextCount(0).verifyComplete();
        verify(clientService, times(1)).deleteClient(any());
    }

    @Test
    void testDeleteClientError() {
        when(clientService.deleteClient(any()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        StepVerifier.create(clientController.deleteClient(any()))
                .expectError(RuntimeException.class)
                .verify();
        verify(clientService, times(1)).deleteClient(any());
    }
}