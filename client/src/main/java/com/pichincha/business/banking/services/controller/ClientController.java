package com.pichincha.business.banking.services.controller;

import com.pichincha.business.banking.services.service.ClientService;
import com.pichincha.business.banking.services.service.dto.ClientRequestDTO;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/list")
    public Mono<ResponseEntity<Flux<ClientResponseDTO>>> getClientByFilter(){
        log.info("|-> Get Client By Filter");
        return clientService.getClientsByFilter()
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Client By Filter finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Client By Filter finished successfully"));
    }
    @GetMapping("/{id}/view")
    public Mono<ResponseEntity<ClientResponseDTO>> getClientById(@PathVariable String id){
        log.info("|-> Get Client By Id");
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Get Client By Id finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Get Client By Id finished successfully"));
    }
    @PostMapping("/create")
    public Mono<ResponseEntity<ClientResponseDTO>> postClient(@RequestBody ClientRequestDTO clientRequestDTO){
        log.info("|-> Post Client");
        return clientService.postClient(clientRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Post Client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Post Client finished successfully"));
    }

    @PutMapping("/{id}/update")
    public Mono<ResponseEntity<ClientResponseDTO>> putClient(@PathVariable String id, @RequestBody ClientRequestDTO clientRequestDTO){
        log.info("|-> Put Client");
        return clientService.putClient(id, clientRequestDTO)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Put Client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Put Client finished successfully"));
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable String id){
        log.info("|-> Delete Client");
        return clientService.deleteClient(id)
                .map(ResponseEntity::ok)
                .doOnError(e -> log.error("<-| Delete Client finished with error: [{}]", e.getMessage()))
                .doOnSuccess(e -> log.info("<-| Delete Client finished successfully"));
    }
}
