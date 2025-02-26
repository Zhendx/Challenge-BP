package com.business.banking.client.infrastructure.input.adapter.rest.impl;

import com.business.banking.client.application.input.port.ClientServicePort;
import com.business.banking.client.domain.Client;
import com.business.banking.client.infrastructure.input.adapter.rest.mapper.ClientMapper;
import com.business.banking.client.util.MockObjects;
import com.business.banking.client.util.PorpertiesTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(value = ClientController.class)
@ExtendWith(MockitoExtension.class)
@ImportAutoConfiguration({PorpertiesTest.class})
class ClientControllerTest {
    @MockBean
    private ClientServicePort clientServicePort;
    @MockBean
    private ClientMapper clientMapper;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PorpertiesTest propertiesTest;

    @Test
    void getClientsByFilterSuccess() {
        when(clientServicePort.getClientByFilter())
                .thenReturn(Flux.just(MockObjects.buildClient()));
        when(clientMapper.toClient((Client) any()))
                .thenReturn(MockObjects.buildClientResponse());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(propertiesTest.getClient()
                                .getPath())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void postClientSuccess() {
        when(clientMapper.toClient((Client) any()))
                .thenReturn(MockObjects.buildClientResponse());
        when(clientServicePort.postClient(any()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path(propertiesTest.getClient()
                                .getPath())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(MockObjects.buildPostClientRequest()), MockObjects.buildPostClientRequest().getClass())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }
}