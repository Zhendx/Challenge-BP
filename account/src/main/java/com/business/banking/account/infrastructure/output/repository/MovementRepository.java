package com.business.banking.account.infrastructure.output.repository;

import com.business.banking.account.domain.*;
import com.business.banking.account.infrastructure.output.repository.entity.MovementEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MovementRepository {
    Mono<Void> deleteMovement(String movementId);

    Flux<MovementEntity> getMovementsByFilter();

    Mono<MovementEntity> getMovementById(String movementId);

    Mono<Void> postMovement(MovementEntity movement);

    Flux<MovementEntity> getMovementByRangeDate(Long accountId, LocalDate startDate, LocalDate endDate);
}
