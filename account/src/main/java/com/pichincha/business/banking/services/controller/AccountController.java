package com.pichincha.business.banking.services.controller;

import com.pichincha.business.banking.services.service.AccountService;
import com.pichincha.business.banking.services.service.dto.AccountRequestDTO;
import com.pichincha.business.banking.services.service.dto.AccountResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/list")
    public Mono<ResponseEntity<Flux<AccountResponseDTO>>> getClientByFilter(){
        log.info("|-> Get Account By Filter");
        return accountService.getAccountsByFilter()
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Account By Filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Account By Filter finished successfully"));
    }

    @GetMapping("/{number}/view")
    public Mono<ResponseEntity<AccountResponseDTO>> getAccountById(@PathVariable String number){
        log.info("|-> Get Account By Id");
        return accountService.getAccountById(number)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Account By Id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Account By Id finished successfully"));
    }
    @PostMapping("/create")
    public Mono<ResponseEntity<AccountResponseDTO>> postAccount(@RequestBody AccountRequestDTO accountRequestDTO){
        log.info("|-> Post Account");
        return accountService.postAccount(accountRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post Account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post Account finished successfully"));
    }

    @PutMapping("/{number}/update")
    public Mono<ResponseEntity<AccountResponseDTO>> putClient(@PathVariable String number, @RequestBody AccountRequestDTO accountRequestDTO){
        log.info("|-> Put Client");
        return accountService.putAccount(number, accountRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put Account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put Account finished successfully"));
    }

    @DeleteMapping("/{number}/delete")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable String number){
        log.info("|-> Delete Account");
        return accountService.deleteAccount(number)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete Account finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete Account finished successfully"));
    }
}
