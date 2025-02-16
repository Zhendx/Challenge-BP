package com.business.banking.account.application.service;

import com.business.banking.account.application.input.port.MovementServicePort;
import com.business.banking.account.application.output.port.SpClientPort;
import com.business.banking.account.domain.*;
import com.business.banking.account.infrastructure.exception.AppException;
import com.business.banking.account.infrastructure.exception.custom.CustomError;
import com.business.banking.account.infrastructure.output.mapper.AccountEntityMapper;
import com.business.banking.account.infrastructure.output.mapper.MovementEntityMapper;
import com.business.banking.account.infrastructure.output.repository.AccountReactiveRepository;
import com.business.banking.account.infrastructure.output.repository.MovementReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovementServiceAdapter implements MovementServicePort {
    private final MovementReactiveRepository movementReactiveRepository;
    private final AccountReactiveRepository accountReactiveRepository;
    private final MovementEntityMapper movementEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final SpClientPort spClientPort;

    @Override
    public Mono<Void> deleteMovement(String movementId) {
        log.info("|--> Starting to delete movement");
        return movementReactiveRepository.findById(Long.valueOf(movementId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .flatMap(movement -> movementReactiveRepository.deleteById(movement.getMovementId())
                        .doOnError(error -> log.error("<--| Error to delete movement! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to delete movement!")))
                .then();
    }

    @Override
    public Flux<Movement> getMovementsByFilter() {
        log.info("|--> Starting to get movement by filter");
        return movementReactiveRepository.findAll()
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(movementEntityMapper::toMovement)
                .doOnError(error -> log.error("<--| Error to get movement by filter! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<--| Success to get movement by filter!"));
    }

    @Override
    public Mono<Movement> getMovementById(String movementId) {
        log.info("|--> Starting to get movement by id");
        return movementReactiveRepository.findById(Long.valueOf(movementId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException)))
                .map(movementEntityMapper::toMovement)
                .doOnError(error -> log.error("<--| Error to get movement by id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to get movement by id!"));
    }

    @Override
    public Mono<Void> patchMovement(String movementId, PatchMovementRequest patchMovementRequest) {
        log.info("|--> Starting to patch movement");
        return movementReactiveRepository.findById(Long.valueOf(movementId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(movementEntityMapper::toMovement)
                .flatMap(movement -> {
                    movement.setDate(patchMovementRequest.getDate());
                    movement.setType(patchMovementRequest.getType());
                    return movementReactiveRepository.save(movementEntityMapper.toMovementEntity(movement))
                            .doOnError(error -> log.error("<--| Error to patch movement! -> {}", error.getMessage()))
                            .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                            .doOnNext(success -> log.info("<--| Success to patch movement!"));
                })
                .then();
    }

    @Override
    public Mono<Void> postMovement(Movement movement) {
        log.info("|--> Starting to post movement");
        return movementReactiveRepository.save(movementEntityMapper.toMovementEntity(movement))
                .doOnError(error -> log.error("<--| Error to post movement! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to post movement!"))
                .then();
    }

    @Override
    public Mono<Void> putMovement(String movementId, PutMovementRequest putMovementRequest) {
        log.info("|--> Starting to put movement");
        return movementReactiveRepository.findById(Long.valueOf(movementId))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(movementEntityMapper::toMovement)
                .map(movement -> updateMovement(movement, putMovementRequest))
                .flatMap(movement -> movementReactiveRepository.save(movementEntityMapper.toMovementEntityPut(movement))
                        .doOnError(error -> log.error("<--| Error to put movement! -> {}", error.getMessage()))
                        .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                        .doOnSuccess(success -> log.info("<--| Success to put movement!")))
                .then();
    }

    @Override
    public Flux<MovementReportResponse> getMovementByRangeDate(String accountNumber, String startDate, String endDate) {
        log.info("|--> Starting to get movement by range date");
        return accountReactiveRepository.findByNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .flatMapMany(account -> spClientPort.getClientById(String.valueOf(account.getClientId()))
                        .flatMapMany(client -> movementReactiveRepository.findByAccountIdAndDateRange(account.getAccountId(), LocalDate.parse(startDate), LocalDate.parse(endDate))
                                .flatMap(movement -> {
                                    MovementReportResponse movementReportResponse = new MovementReportResponse();
                                    movementReportResponse.setAvailableBalance(movement.getType().equalsIgnoreCase("Deposito") ?
                                            movement.getBalance().add(movement.getValue()) :
                                            movement.getBalance().subtract(movement.getValue())
                                    );
                                    return Mono.just(movementEntityMapper.toMovementReportResponse(accountEntityMapper.toAccount(account), client, movementEntityMapper.toMovement(movement), movementReportResponse))
                                            .doOnError(error -> log.error("<--| Error to get movement by range date! -> {}", error.getMessage()))
                                            .doOnSuccess(success -> log.info("<--| Success to get movement by range date!"));
                                })
                        )
                );
    }

    @Override
    public Mono<MovementTransactionResponse> postMovementTransaction(String accountNumber, PostMovementTransactionRequest postMovementTransactionRequest) {
        log.info("|--> Starting to post Movement Transaction");
        return accountReactiveRepository.findByNumber(accountNumber)
                .flatMap(account -> {
                    if (postMovementTransactionRequest.getValue().compareTo(BigDecimal.ZERO) < 0) {
                        throw new AppException(CustomError.BalanceException);
                    }
                    if (postMovementTransactionRequest.getType().getDescription().equalsIgnoreCase("Retiro") && account.getBalance().compareTo(postMovementTransactionRequest.getValue()) < 0) {
                        throw new AppException(CustomError.RetreatException);
                    }
                    Movement movement = createMovement(postMovementTransactionRequest, accountEntityMapper.toAccount(account));
                    log.info("|--> Starting to save account transaction");
                    log.info("|--> Starting to save movement transaction");
                    return accountReactiveRepository.save(accountEntityMapper.toAccountEntity(updateAccountBalance(accountEntityMapper.toAccount(account), postMovementTransactionRequest)))
                            .doOnError(error -> log.error("<--| Error to save account transaction! -> {}", error.getMessage()))
                            .doOnNext(success -> log.info("<--| Success to save account transaction!"))
                            .then(movementReactiveRepository.save(movementEntityMapper.toMovementEntity(movement))
                                    .doOnError(error -> log.error("<--| Error to save movement transaction! -> {}", error.getMessage()))
                                    .doOnNext(success -> log.info("<--| Success to save movement transaction!")))
                            .thenReturn(movementEntityMapper.movementToMovementTransactionResponse(movement, account));
                })
                .doOnError(error -> log.error("<--| Error to post Movement Transaction! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<--| Success to post Movement Transaction!"));
    }

    private Movement updateMovement(Movement movement, PutMovementRequest putMovementRequest) {
        movement.setDate(putMovementRequest.getDate());
        movement.setType(putMovementRequest.getType());
        movement.setValue(putMovementRequest.getValue());
        movement.setBalance(putMovementRequest.getBalance());
        return movement;
    }

    private Movement createMovement(PostMovementTransactionRequest postMovementTransactionRequest, Account account) {
        Movement movement = new Movement();
        movement.setDate(LocalDate.now());
        movement.setType(postMovementTransactionRequest.getType());
        movement.setValue(postMovementTransactionRequest.getValue());
        movement.setBalance(account.getBalance());
        movement.setAccountId(account.getAccountId());
        return movement;
    }

    private Account updateAccountBalance(Account account, PostMovementTransactionRequest postMovementTransactionRequest) {
        account.setBalance(postMovementTransactionRequest.getType().getCode().equalsIgnoreCase("Deposito") ?
                account.getBalance().add(postMovementTransactionRequest.getValue()) :
                account.getBalance().subtract(postMovementTransactionRequest.getValue()));
        return account;
    }
}
