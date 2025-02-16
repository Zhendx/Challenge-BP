package com.business.banking.account.application.output.port;

import com.business.banking.account.domain.Client;
import reactor.core.publisher.Mono;

public interface SpClientPort {
    Mono<Client> getClientById(String clientId);
}
