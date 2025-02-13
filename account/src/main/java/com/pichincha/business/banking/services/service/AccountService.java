package com.pichincha.business.banking.services.service;

import com.pichincha.business.banking.services.service.dto.AccountRequestDTO;
import com.pichincha.business.banking.services.service.dto.AccountResponseDTO;
import org.springframework.data.redis.connection.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Flux<AccountResponseDTO>> getAccountsByFilter();
    Mono<AccountResponseDTO> getAccountById(String number);
    Mono<AccountResponseDTO> postAccount(AccountRequestDTO accountRequestDTO);
    Mono<AccountResponseDTO> putAccount(String number, AccountRequestDTO accountRequestDTO);
    Mono<Void> deleteAccount(String number);
}
