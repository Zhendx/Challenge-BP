package com.business.banking.account.infrastructure.output.repository;

import com.business.banking.account.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Void> deleteAccount(String accountId);

    Flux<AccountEntity> getAccountByFilter();

    Mono<AccountEntity> getAccountById(String accountId);

    Mono<Void> postAccount(AccountEntity account);

    Mono<AccountEntity> getAccountByNumber(String accountNumber);

}
