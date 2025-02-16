package com.business.banking.account.infrastructure.input.adapter.rest.impl;

import com.business.banking.account.application.input.port.AccountServicePort;
import com.business.banking.account.application.input.port.MovementServicePort;
import com.business.banking.account.infrastructure.input.adapter.rest.mapper.AccountMapper;
import com.business.banking.account.infrastructure.input.adapter.rest.mapper.MovementMapper;
import com.business.banking.account.infrastructure.input.adapter.rest.SupportApi;
import com.business.banking.account.infrastructure.input.adapter.rest.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController implements SupportApi {
    private final AccountServicePort accountServicePort;
    private final MovementServicePort movementServicePort;
    private final AccountMapper accountMapper;
    private final MovementMapper movementMapper;

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String accountId, ServerWebExchange exchange) {
        log.info("|-> Start delete account");
        return accountServicePort.deleteAccount(accountId)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete account finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAccountByFilter(ServerWebExchange exchange) {
        log.info("|-> Start get account by filter");
        return accountServicePort.getAccountByFilter()
                .map(accountMapper::toAccount)
                .collectList()
                .map(response -> ResponseEntity.ok().body(Flux.fromIterable(response)))
                .doOnError(e -> log.error("<-| Get account by filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get account by filter finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<GetAccountByIdResponse>> getAccountById(String accountId, ServerWebExchange exchange) {
        log.info("|-> Start get account by id");
        return accountServicePort.getAccountById(accountId)
                .map(accountMapper::toGetAccountByIdResponse)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get account by id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get account by id finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccount(String accountId, Mono<PatchAccountRequest> patchAccountRequest, ServerWebExchange exchange) {
        log.info("|-> Start patch account");
        return patchAccountRequest
                .flatMap(patchAccount -> accountServicePort.patchAccount(accountId, accountMapper.toPatchAccountRequest(patchAccount)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Patch account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Patch account finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> postAccount(Mono<PostAccountRequest> postAccountRequest, ServerWebExchange exchange) {
        log.info("|-> Start post account");
        return postAccountRequest
                .flatMap(postAccount -> accountServicePort.postAccount(accountMapper.toAccount(postAccount)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post account finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccount(String accountId, Mono<PutAccountRequest> putAccountRequest, ServerWebExchange exchange) {
        log.info("|-> Start put account");
        return putAccountRequest
                .flatMap(putClient -> accountServicePort.putAccount(accountId, accountMapper.toPutAccountRequest(putClient)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put account finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccountMovement(String accountId, ServerWebExchange exchange) {
        log.info("|-> Start delete movement");
        return movementServicePort.deleteMovement(accountId)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete movement finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Flux<Movement>>> getAccountMovementByFilter(ServerWebExchange exchange) {
        log.info("|-> Start get movement by filter");
        return movementServicePort.getMovementsByFilter()
                .map(movementMapper::toMovement)
                .collectList()
                .map(response -> ResponseEntity.ok().body(Flux.fromIterable(response)))
                .doOnError(e -> log.error("<-| Get movement by filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get movement by filter finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<GetAccountMovementByIdResponse>> getAccountMovementById(String accountId, ServerWebExchange exchange) {
        log.info("|-> Start get movement by id");
        return movementServicePort.getMovementById(accountId)
                .map(movementMapper::toGetMovementByIdResponse)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get movement by id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get movement by id finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccountMovement(String accountId, Mono<PatchAccountMovementRequest> patchAccountMovementRequest, ServerWebExchange exchange) {
        log.info("|-> Start patch movement");
        return patchAccountMovementRequest
                .flatMap(patchMovement -> movementServicePort.patchMovement(accountId, movementMapper.toPatchMovementRequest(patchMovement)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Patch movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Patch movement finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> postMovement(Mono<PostMovementRequest> postMovementRequest, ServerWebExchange exchange) {
        log.info("|-> Start post movement");
        return postMovementRequest
                .flatMap(postMovement -> movementServicePort.postMovement(movementMapper.toMovement(postMovement)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post movement finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccountMovement(String accountId, Mono<PutAccountMovementRequest> putAccountMovementRequest, ServerWebExchange exchange) {
        log.info("|-> Start put movement");
        return putAccountMovementRequest
                .flatMap(putMovement -> movementServicePort.putMovement(accountId, movementMapper.toPutMovementRequest(putMovement)))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put movement finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<Flux<MovementReportResponse>>> getAccountMovementReport(String accountNumber, String startDate, String endDate, ServerWebExchange exchange) {
        log.info("|-> Get Movement By Range Date");
        return movementServicePort.getMovementByRangeDate(accountNumber, startDate, endDate)
                .map(movementMapper::toMovementReportResponse)
                .collectList()
                .map(response -> ResponseEntity.ok().body(Flux.fromIterable(response)))
                .doOnError(e -> log.error("<-| Get Movement By Range Date finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Movement By Range Date finished successfully"));
    }

    @Override
    public Mono<ResponseEntity<PostAccountMovementTransactionResponse>> postAccountMovementTransaction(String accountNumber, Mono<PostAccountMovementTransactionRequest> postAccountMovementTransactionRequest, ServerWebExchange exchange) {
        log.info("|-> Post Movement Transaction");
        return postAccountMovementTransactionRequest
                .flatMap(postTransaction -> movementServicePort
                        .postMovementTransaction(accountNumber, movementMapper.toPostMovementTransactionRequest(postTransaction))
                        .map(movementMapper::toPostAccountMovementTransactionResponse))
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post Movement Transaction finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post Movement Transaction finished successfully"));
    }
}
