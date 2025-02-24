package com.business.banking.account.infrastructure.output.repository.impl;

import com.business.banking.account.infrastructure.output.repository.AccountReactiveRepository;
import com.business.banking.account.infrastructure.output.repository.AccountRepository;
import com.business.banking.account.infrastructure.output.repository.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountReactiveRepository accountReactiveRepository;

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        log.info("|---> Starting delete account entity");
        return accountReactiveRepository.deleteById(Long.valueOf(accountId))
                .doOnError(error -> log.error("<---| Error to delete account entity! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to delete account entity!"));
    }

    @Override
    public Flux<AccountEntity> getAccountByFilter() {
        log.info("|---> Starting find all account entity");
        return accountReactiveRepository.findAll()
                .doOnError(error -> log.error("<---| Error to find all account entity! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<---| Success to find all account entity!"));
    }

    @Override
    public Mono<AccountEntity> getAccountById(String accountId) {
        log.info("|---> Starting find by id account entity");
        return accountReactiveRepository.findById(Long.valueOf(accountId))
                .doOnError(error -> log.error("<---| Error to find by id account entity! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to find by id account entity!"));
    }

    @Override
    public Mono<Void> postAccount(AccountEntity account) {
        log.info("|---> Starting save account entity");
        return accountReactiveRepository.save(account)
                .doOnError(error -> log.error("<---| Error to save account entity! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to save account entity!"))
                .then();
    }

    @Override
    public Mono<AccountEntity> getAccountByNumber(String accountNumber) {
        log.info("|---> Starting get account entity by number");
        return accountReactiveRepository.findByNumber(accountNumber)
                .doOnError(error -> log.error("<---| Error to get account entity by number! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to get account entity by number!"));
    }
}
