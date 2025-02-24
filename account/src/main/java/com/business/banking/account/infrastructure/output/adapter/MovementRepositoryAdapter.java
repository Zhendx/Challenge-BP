package com.business.banking.account.infrastructure.output.adapter;

import com.business.banking.account.application.output.port.MovementRepositoryPort;
import com.business.banking.account.domain.*;
import com.business.banking.account.infrastructure.exception.AppException;
import com.business.banking.account.infrastructure.exception.custom.CustomError;
import com.business.banking.account.infrastructure.output.mapper.AccountEntityMapper;
import com.business.banking.account.infrastructure.output.mapper.MovementEntityMapper;
import com.business.banking.account.infrastructure.output.repository.AccountRepository;
import com.business.banking.account.infrastructure.output.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepositoryPort {
    private final MovementRepository movementRepository;
    private final MovementEntityMapper movementEntityMapper;

    @Override
    public Mono<Void> deleteMovement(String movementId) {
        return movementRepository.deleteMovement(movementId);
    }

    @Override
    public Flux<Movement> getMovementsByFilter() {
        return movementRepository.getMovementsByFilter()
                .map(movementEntityMapper::toMovement);
    }

    @Override
    public Mono<Movement> getMovementById(String movementId) {
        return movementRepository.getMovementById(movementId)
                .map(movementEntityMapper::toMovement);
    }

    @Override
    public Mono<Void> patchMovement(String movementId, PatchMovementRequest patchMovementRequest) {
        return movementRepository.getMovementById(movementId)
                .flatMap(movement -> {
                    movement.setDate(patchMovementRequest.getDate());
                    movement.setType(patchMovementRequest.getType().getCode());
                    movement.setTypeDescription(patchMovementRequest.getType().getDescription());
                    return movementRepository.postMovement(movement);
                })
                .then();
    }

    @Override
    public Mono<Void> postMovement(Movement movement) {
        return movementRepository.postMovement(movementEntityMapper.toMovementEntity(movement));
    }

    @Override
    public Mono<Void> putMovement(String movementId, PutMovementRequest putMovementRequest) {
        return movementRepository.getMovementById(movementId)
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(movementEntityMapper::toMovement)
                .map(movement -> updateMovement(movement, putMovementRequest))
                .flatMap(movement -> movementRepository.postMovement(movementEntityMapper.toMovementEntityPut(movement)))
                .then();
    }

    @Override
    public Flux<Movement> getMovementByRangeDate(String accountId, String startDate, String endDate) {
        return movementRepository.getMovementByRangeDate(Long.valueOf(accountId), LocalDate.parse(startDate), LocalDate.parse(endDate))
                .map(movementEntityMapper::toMovement);
    }

    private Movement updateMovement(Movement movement, PutMovementRequest putMovementRequest) {
        movement.setDate(putMovementRequest.getDate());
        movement.setType(putMovementRequest.getType());
        movement.setValue(putMovementRequest.getValue());
        movement.setBalance(putMovementRequest.getBalance());
        return movement;
    }
}
