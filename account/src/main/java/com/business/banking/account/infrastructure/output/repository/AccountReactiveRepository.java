package com.business.banking.account.infrastructure.output.repository;

import com.business.banking.account.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    @Query("SELECT a FROM account a WHERE a.number = :number")
    Mono<AccountEntity> findByNumber(String number);
}
