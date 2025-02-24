package com.business.banking.account.infrastructure.output.adapter;

import com.business.banking.account.application.output.port.AccountRepositoryPort;
import com.business.banking.account.domain.Account;
import com.business.banking.account.domain.PatchAccountRequest;
import com.business.banking.account.domain.PutAccountRequest;
import com.business.banking.account.domain.Type;
import com.business.banking.account.infrastructure.output.mapper.AccountEntityMapper;
import com.business.banking.account.infrastructure.output.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {
    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public Flux<Account> getAccountByFilter() {
        return accountRepository.getAccountByFilter()
                .map(accountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> getAccountById(String accountId) {
        return accountRepository.getAccountById(accountId)
                .map(accountEntityMapper::toAccount);
    }

    @Override
    public Mono<Void> patchAccount(String accountId, PatchAccountRequest patchAccountRequest) {
        return accountRepository.getAccountById(accountId)
                .flatMap(account -> {
                    account.setBalance(patchAccountRequest.getBalance());
                    account.setStatus(patchAccountRequest.getStatus());
                    return accountRepository.postAccount(account);
                })
                .then();
    }

    @Override
    public Mono<Void> postAccount(Account account) {
        return accountRepository.postAccount(accountEntityMapper.toAccountEntity(account));
    }

    @Override
    public Mono<Void> putAccount(String accountId, PutAccountRequest putAccountRequest) {
        return accountRepository.getAccountById(accountId)
                .map(accountEntityMapper::toAccount)
                .map(account -> updateAccount(account, putAccountRequest))
                .flatMap(account -> accountRepository.postAccount(accountEntityMapper.toAccountEntityPut(account)))
                .then();
    }

    @Override
    public Mono<Account> findByNumber(String accountNumber) {
        return accountRepository.getAccountByNumber(accountNumber)
                .map(accountEntityMapper::toAccount);
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
