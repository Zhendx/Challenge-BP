package com.business.banking.account.application.service;

import com.business.banking.account.application.input.port.AccountServicePort;
import com.business.banking.account.domain.Account;
import com.business.banking.account.domain.PatchAccountRequest;
import com.business.banking.account.domain.PutAccountRequest;
import com.business.banking.account.domain.Type;
import com.business.banking.account.infrastructure.exception.AppException;
import com.business.banking.account.infrastructure.exception.custom.CustomError;
import com.business.banking.account.infrastructure.output.mapper.AccountEntityMapper;
import com.business.banking.account.infrastructure.output.repository.AccountReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceAdapter implements AccountServicePort {
    private final AccountReactiveRepository accountReactiveRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        log.info("|--> Starting to delete account");
        return accountReactiveRepository.findById(Long.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .flatMap(account -> accountReactiveRepository.deleteById(account.getAccountId())
                        .doOnError(error -> log.error("<--| Error to delete account! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to delete account!")))
                .then();
    }

    @Override
    public Flux<Account> getAccountByFilter() {
        log.info("|--> Starting to get account by filter");
        return accountReactiveRepository.findAll()
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(accountEntityMapper::toAccount)
                .doOnError(error -> log.error("<--| Error to get account by filter! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<--| Success to get account by filter!"));
    }

    @Override
    public Mono<Account> getAccountById(String accountId) {
        log.info("|--> Starting to get account by id");
        return accountReactiveRepository.findById(Long.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(accountEntityMapper::toAccount)
                .doOnError(error -> log.error("<--| Error to get account by id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to get account by id!"));
    }

    @Override
    public Mono<Void> patchAccount(String accountId, PatchAccountRequest patchAccountRequest) {
        log.info("|--> Starting to patch account");
        return accountReactiveRepository.findById(Long.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(accountEntityMapper::toAccount)
                .flatMap(account -> {
                    account.setBalance(patchAccountRequest.getBalance());
                    account.setStatus(patchAccountRequest.getStatus());
                    return accountReactiveRepository.save(accountEntityMapper.toAccountEntity(account))
                            .doOnError(error -> log.error("<--| Error to patch account! -> {}", error.getMessage()))
                            .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                            .doOnNext(success -> log.info("<--| Success to patch account!"));
                })
                .then();
    }

    @Override
    public Mono<Void> postAccount(Account account) {
        log.info("|--> Starting to post account");
        return accountReactiveRepository.save(accountEntityMapper.toAccountEntity(account))
                .doOnError(error -> log.error("<--| Error to post account! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to post account!"))
                .then();
    }

    @Override
    public Mono<Void> putAccount(String accountId, PutAccountRequest putAccountRequest) {
        log.info("|--> Starting to put account");
        return accountReactiveRepository.findById(Long.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(accountEntityMapper::toAccount)
                .map(account -> updateAccount(account, putAccountRequest))
                .flatMap(client -> accountReactiveRepository.save(accountEntityMapper.toAccountEntityPut(client))
                        .doOnError(error -> log.error("<--| Error to put client! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to put client!")))
                .then();
    }

    private Account updateAccount(Account account, PutAccountRequest putAccountRequest){
        account.setBalance(putAccountRequest.getBalance());
        account.setStatus(putAccountRequest.getStatus());
        account.setNumber(putAccountRequest.getNumber());
        account.setClientId(putAccountRequest.getClientId());
        account.setType(Type.builder().code(putAccountRequest.getType().getCode()).description(putAccountRequest.getType().getDescription()).build());
        return account;
    }
}
