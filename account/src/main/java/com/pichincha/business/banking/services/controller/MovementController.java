package com.pichincha.business.banking.services.controller;

import com.pichincha.business.banking.services.service.MovementService;
import com.pichincha.business.banking.services.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {
    private final MovementService movementService;

    @GetMapping("/list")
    public Mono<ResponseEntity<Flux<MovementResponseDTO>>> getMovementByFilter(){
        log.info("|-> Get Movement By Filter");
        return movementService.getMovementsByFilter()
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Movement By Filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Movement By Filter finished successfully"));
    }

    @GetMapping("/{id}/view")
    public Mono<ResponseEntity<MovementResponseDTO>> getMovementById(@PathVariable String id){
        log.info("|-> Get Movement By Id");
        return movementService.getMovementById(id)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Movement By Id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Movement By Id finished successfully"));
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<MovementResponseDTO>> postMovement(@RequestBody MovementRequestDTO movementRequestDTO){
        log.info("|-> Post Movement");
        return movementService.postMovement(movementRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post Movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post Movement finished successfully"));
    }

    @PutMapping("/{id}/update")
    public Mono<ResponseEntity<MovementResponseDTO>> putMovement(@PathVariable String id, @RequestBody MovementRequestDTO movementRequestDTO){
        log.info("|-> Put Movement");
        return movementService.putMovement(id, movementRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put Movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put Movement finished successfully"));
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteMovement(@PathVariable String id){
        log.info("|-> Delete Movement");
        return movementService.deleteMovement(id)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete Movement finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete Movement finished successfully"));
    }

    @GetMapping("/{id}/report")
    public Mono<ResponseEntity<Flux<MovementReportResponseDTO>>> getMovementByRangeDate(@PathVariable String id, @RequestParam Date dateInitial, @RequestParam Date dateFinal){
        log.info("|-> Get Movement By Range Date");
        return movementService.getMovementByRangeDate(id, dateInitial, dateFinal)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Movement By Range Date finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Movement By Range Date finished successfully"));
    }

    @PostMapping("/{id}/transaction")
    public Mono<ResponseEntity<MovementTransactionResponseDTO>> postMovementTransaction(@PathVariable String id, @RequestBody MovementTransactionRequestDTO movementTransactionRequestDTO){
        log.info("|-> Post Movement Transaction");
        return movementService.postMovementTransaction(id, movementTransactionRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post Movement Transaction finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post Movement Transaction finished successfully"));
    }
}
