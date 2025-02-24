package com.business.banking.account.infrastructure.output.repository.impl;

import com.business.banking.account.domain.Movement;
import com.business.banking.account.domain.MovementReportResponse;
import com.business.banking.account.domain.MovementTransactionResponse;
import com.business.banking.account.domain.PostMovementTransactionRequest;
import com.business.banking.account.infrastructure.output.repository.MovementReactiveRepository;
import com.business.banking.account.infrastructure.output.repository.MovementRepository;
import com.business.banking.account.infrastructure.output.repository.entity.MovementEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovementRepositoryImpl implements MovementRepository {
    public final MovementReactiveRepository movementReactiveRepository;

    @Override
    public Mono<Void> deleteMovement(String movementId) {
        log.info("|---> Starting delete movement entity");
        return movementReactiveRepository.deleteById(Long.valueOf(movementId))
                .doOnError(error -> log.error("<---| Error to delete movement entity! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<---| Success to delete movement entity!"));
    }

    @Override
    public Flux<MovementEntity> getMovementsByFilter() {
        log.info("|---> Starting find all movement entity");
        return movementReactiveRepository.findAll()
                .doOnError(error -> log.error("<---| Error to find all account entity! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<---| Success to find all account entity!"));
    }

    @Override
    public Mono<MovementEntity> getMovementById(String movementId) {
        log.info("|---> Starting find by id movement entity");
        return movementReactiveRepository.findById(Long.valueOf(movementId))
                .doOnError(error -> log.error("<---| Error to find by id movement entity! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<---| Success to find by id movement entity!"));
    }

    @Override
    public Mono<Void> postMovement(MovementEntity movement) {
        log.info("|---> Starting save movement entity");
        return movementReactiveRepository.save(movement)
                .doOnError(error -> log.error("<---| Error to save movement entity! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<---| Success to save movement entity!"))
                .then();
    }

    @Override
    public Flux<MovementEntity> getMovementByRangeDate(Long accountId, LocalDate startDate, LocalDate endDate) {
        log.info("|---> Starting get movement by range movement entity");
        return movementReactiveRepository.findByAccountIdAndDateRange(accountId, startDate, endDate)
                .doOnError(error -> log.error("<---| Error to get movement by range movement entity! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<---| Success to get movement by range movement entity!"));
    }
}
