package com.pichincha.business.banking.services.service.impl;

import com.pichincha.business.banking.services.exception.AppException;
import com.pichincha.business.banking.services.exception.custom.CustomError;
import com.pichincha.business.banking.services.repository.impl.AccountRepository;
import com.pichincha.business.banking.services.repository.impl.MovementRepository;
import com.pichincha.business.banking.services.service.MovementService;
import com.pichincha.business.banking.services.service.dto.*;
import com.pichincha.business.banking.services.service.entity.Account;
import com.pichincha.business.banking.services.service.entity.Movement;
import com.pichincha.business.banking.services.service.mapper.MovementMapper;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Override
    public Mono<Flux<MovementResponseDTO>> getMovementsByFilter() {
        log.info("|--> Starting to get Movement By Filter");
        return Mono.just(Flux.fromIterable(movementRepository.findAll())
                        .map(MovementMapper.INSTANCE::movementToMovementResponseDTO)
                        .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException))))
                .doOnError(error -> log.error("<--| Error to get Movement By Filter! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Movement By Filter!"));
    }

    @Override
    public Mono<MovementResponseDTO> getMovementById(String id) {
        log.info("|--> Starting to get Movement By Id");
        return Mono.just(movementRepository.findById(Long.valueOf(id)).orElseThrow(() -> new AppException(CustomError.NotFoundDataException)))
                .map(MovementMapper.INSTANCE::movementToMovementResponseDTO)
                .doOnError(error -> log.error("<--| Error to get Movement By Id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Movement By Id!"));
    }

    @Override
    public Mono<MovementResponseDTO> postMovement(MovementRequestDTO movementRequestDTO) {
        log.info("|--> Starting to post Movement");
        return Mono.just(movementRequestDTO)
                .flatMap(movementRequest -> Mono.fromCallable(() -> movementRepository.save(MovementMapper.INSTANCE.movementRequestDTOToMovement(movementRequest)))
                        .map(MovementMapper.INSTANCE::movementToMovementResponseDTO))
                .doOnError(error -> log.error("<--| Error to post Movement! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to post Movement!"));
    }

    @Override
    public Mono<MovementResponseDTO> putMovement(String id, MovementRequestDTO movementRequestDTO) {
        log.info("|--> Starting to put Movement");
        return Mono.just(movementRepository.findById(Long.valueOf(id)).orElseThrow(() -> new AppException(CustomError.NotFoundDataException)))
                .map(movement -> updateMovement(movement, movementRequestDTO))
                .map(movementRepository::save)
                .map(MovementMapper.INSTANCE::movementToMovementResponseDTO)
                .doOnError(error -> log.error("<--| Error to put Movement! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to put Movement!"));
    }

    private Movement updateMovement(Movement movement, MovementRequestDTO movementRequestDTO) {
        movement.setDate(movementRequestDTO.getDate());
        movement.setBalance(movementRequestDTO.getBalance());
        movement.setType(movementRequestDTO.getType());
        movement.setValue(movementRequestDTO.getValue());
        movement.setIdAccount(movementRequestDTO.getIdAccount());
        return movement;
    }

    @Override
    public Mono<Void> deleteMovement(String id) {
        log.info("|--> Starting to delete Movement");
        return Mono.just(movementRepository.findById(Long.valueOf(id)).orElseThrow(() -> new AppException(CustomError.NotFoundDataException)))
                .map(movement -> {
                    movementRepository.deleteById(Long.valueOf(id));
                    return Mono.just("Movement deleted successfully.");
                })
                .doOnError(error -> log.error("<--| Error to delete Movement! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to delete Movement!"))
                .then();
    }

    @Override
    public Mono<Flux<MovementReportResponseDTO>> getMovementByRangeDate(String id, Date dateInitial, Date dateFinal) {
        log.info("|--> Starting to get Movement By Range Date");
        return Mono.just(movementRepository.findByDateInitialAndDateFinalAndIdentification(id, dateInitial, dateFinal))
                .flatMap(algo ->  Flux.fromIterable(algo)
                        .flatMap(movement -> {
                            movement.setAvailableBalance(movement.getTypeMovement().equalsIgnoreCase("Deposito") ?
                                    movement.getBalance() + movement.getValue():
                                    movement.getBalance() - movement.getValue());
                            return Mono.just(movement);
                        })
                        .collectList()
                        .flatMap(list -> Mono.just(Flux.fromIterable(list)))
                )
                .doOnError(error -> log.error("<--| Error to get Movement By Range Date! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnNext(success -> log.info("<--| Success to get Movement By Range Date!"));
    }

    @Override
    public Mono<MovementTransactionResponseDTO> postMovementTransaction(String id, MovementTransactionRequestDTO movementTransactionRequestDTO) {
        log.info("|--> Starting to post Movement Transaction");
        return Mono.just(accountRepository.findFirstByNumber(id))
                .flatMap(account -> {
                    if(movementTransactionRequestDTO.getValue()<0){
                        throw new AppException(CustomError.BalanceException);
                    }
                    if(movementTransactionRequestDTO.getType().equalsIgnoreCase("Retiro")&&account.getBalance()<movementTransactionRequestDTO.getValue()){
                        throw new AppException(CustomError.RetreatException);
                    }
                    Movement movement = createMovement(movementTransactionRequestDTO, account);
                    updateAccountBalance(account, movementTransactionRequestDTO);
                    accountRepository.save(account);
                    movementRepository.save(movement);
                    return Mono.just(MovementMapper.INSTANCE.movementToMovementTransactionResponseDTO(movement, account));
                })
                .doOnError(error -> log.error("<--| Error to post Movement Transaction! -> {}", error.getMessage()))
                .doOnNext(success -> log.info("<--| Success to post Movement Transaction!"));
    }

    private Movement createMovement(MovementTransactionRequestDTO movementTransactionRequestDTO, Account account) {
        Movement movement = new Movement();
        movement.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        movement.setType(movementTransactionRequestDTO.getType());
        movement.setValue(movementTransactionRequestDTO.getValue());
        movement.setBalance(account.getBalance());
        movement.setIdAccount(account.getId());
        return movement;
    }

    private void updateAccountBalance(Account account, MovementTransactionRequestDTO movementTransactionRequestDTO) {
        account.setBalance(movementTransactionRequestDTO.getType().equalsIgnoreCase("Deposito") ?
                account.getBalance() + movementTransactionRequestDTO.getValue() :
                account.getBalance() - movementTransactionRequestDTO.getValue());
    }
}
