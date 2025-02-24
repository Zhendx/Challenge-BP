package com.business.banking.account.application.output.port;

import com.business.banking.account.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepositoryPort {
    Mono<Void> deleteMovement(String movementId);

    Flux<Movement> getMovementsByFilter();

    Mono<Movement> getMovementById(String movementId);

    Mono<Void> patchMovement(String movementId, PatchMovementRequest patchMovementRequest);

    Mono<Void> postMovement(Movement movement);

    Mono<Void> putMovement(String movementId, PutMovementRequest putMovementRequest);

    Flux<Movement> getMovementByRangeDate(String accountNumber, String startDate, String endDate);
}
