package com.pichincha.business.banking.services.service.impl;

import com.pichincha.business.banking.services.exception.AppException;
import com.pichincha.business.banking.services.exception.custom.CustomError;
import com.pichincha.business.banking.services.repository.impl.ClientRepository;
import com.pichincha.business.banking.services.service.ClientService;
import com.pichincha.business.banking.services.service.dto.ClientRequestDTO;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import com.pichincha.business.banking.services.service.entity.Client;
import com.pichincha.business.banking.services.service.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RedisTemplate template;
    private final ChannelTopic topic;

    @Override
    public Mono<Flux<ClientResponseDTO>> getClientsByFilter() {
        log.info("|--> Starting to get Clients By Filter");
        return Mono.just(Flux.fromIterable(clientRepository.findAll())
                        .map(ClientMapper.INSTANCE::clientToClientResponseDTO)
                        .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException))))
                .doOnError(error -> log.error("<--| Error to get Clients By Filter! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Clients By Filter!"));
    }

    @Override
    public Mono<ClientResponseDTO> getClientById(String id) {
        log.info("|--> Starting to get Clients By Id");
        return Mono.just(clientRepository.findFirstByIdentification(id))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(ClientMapper.INSTANCE::clientToClientResponseDTO)
                .doOnError(error -> log.error("<--| Error to get Clients By Id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Clients By Id!"));
    }

    @Override
    public Mono<ClientResponseDTO> postClient(ClientRequestDTO clientRequestDTO) {
        log.info("|--> Starting to post Client");
        return Mono.just(clientRequestDTO)
                .flatMap(clientRequest -> Mono.fromCallable(() -> clientRepository.save(ClientMapper.INSTANCE.clientRequestDTOToClient(clientRequest)))
                        .map(client -> {
                            template.convertAndSend(topic.getTopic(), String.valueOf(client.getId()));
                            return ClientMapper.INSTANCE.clientToClientResponseDTO(client);
                        }))
                .doOnError(error -> log.error("<--| Error to post Client! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to post Client!"));
    }

    @Override
    public Mono<ClientResponseDTO> putClient(String id, ClientRequestDTO clientRequestDTO) {
        log.info("|--> Starting to put Client");
        return Mono.just(clientRepository.findFirstByIdentification(id))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(client -> updateClient(client, clientRequestDTO))
                .map(clientRepository::save)
                .map(ClientMapper.INSTANCE::clientToClientResponseDTO)
                .doOnError(error -> log.error("<--| Error to put Client! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to put Client!"));
    }

    private Client updateClient(Client client, ClientRequestDTO clientRequestDTO) {
        client.setAge(clientRequestDTO.getAge());
        client.setPassword(clientRequestDTO.getPassword());
        client.setState(clientRequestDTO.getState());
        client.setAddress(clientRequestDTO.getAddress());
        client.setIdentification(clientRequestDTO.getIdentification());
        client.setName(clientRequestDTO.getName());
        client.setPhone(clientRequestDTO.getPhone());
        return client;
    }

    @Override
    public Mono<Void> deleteClient(String id) {
        log.info("|--> Starting to delete Client");
        return Mono.just(clientRepository.findFirstByIdentification(id))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .flatMap(client -> {
                    clientRepository.deleteByIdentification(id);
                    return Mono.empty();
                })
                .doOnError(error -> log.error("<--| Error to delete Client! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to delete Client!"))
                .then();
    }
}
