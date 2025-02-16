package com.business.banking.account.application.input.port;

import com.business.banking.account.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

public interface MovementServicePort {
    Mono<Void> deleteMovement(String movementId);
    Flux<Movement> getMovementsByFilter();
    Mono<Movement> getMovementById(String movementId);
    Mono<Void> patchMovement(String movementId, PatchMovementRequest patchMovementRequest);
    Mono<Void> postMovement(Movement movement);
    Mono<Void> putMovement(String movementId, PutMovementRequest putMovementRequest);
    Flux<MovementReportResponse> getMovementByRangeDate(String accountNumber, String startDate, String endDate);
    Mono<MovementTransactionResponse> postMovementTransaction(String accountNumber, PostMovementTransactionRequest postMovementTransactionRequest);
}
