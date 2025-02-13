package com.pichincha.business.banking.services.service;

import com.pichincha.business.banking.services.service.dto.ClientRequestDTO;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<Flux<ClientResponseDTO>> getClientsByFilter();
    Mono<ClientResponseDTO> getClientById(String id);
    Mono<ClientResponseDTO> postClient(ClientRequestDTO clientRequestDTO);
    Mono<ClientResponseDTO> putClient(String id, ClientRequestDTO clientRequestDTO);
    Mono<Void> deleteClient(String id);
}
