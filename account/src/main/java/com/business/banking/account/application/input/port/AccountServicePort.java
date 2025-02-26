package com.business.banking.account.application.input.port;

import com.business.banking.account.domain.Account;
import com.business.banking.account.domain.PatchAccountRequest;
import com.business.banking.account.domain.PutAccountRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AccountServicePort {
    Mono<Void> deleteAccount(String accountId);
    Flux<Account> getAccountByFilter();
    Mono<Account> getAccountById(String accountId);
    Mono<Void> patchAccount(String accountId, PatchAccountRequest patchAccountRequest);
    Mono<Void> postAccount(Account account);
    Mono<Void> putAccount(String accountId, PutAccountRequest putAccountRequest);
}
