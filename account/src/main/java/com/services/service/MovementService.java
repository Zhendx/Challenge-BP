package com.pichincha.business.banking.services.service;

import com.pichincha.business.banking.services.service.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

public interface MovementService {
    Mono<Void> deleteMovement(String id);
    Mono<Flux<MovementResponseDTO>> getMovementsByFilter();
    Mono<MovementResponseDTO> getMovementById(String id);
    Mono<MovementResponseDTO> postMovement(MovementRequestDTO movementRequestDTO);
    Mono<MovementResponseDTO> putMovement(String id, MovementRequestDTO movementRequestDTO);

    Mono<Flux<MovementReportResponseDTO>> getMovementByRangeDate(String id, Date dateInitial, Date dateFinal);
    Mono<MovementTransactionResponseDTO> postMovementTransaction(String id, MovementTransactionRequestDTO movementTransactionRequestDTO);
}
