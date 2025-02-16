package com.business.banking.account.infrastructure.output.repository;

import com.business.banking.account.infrastructure.output.repository.entity.MovementEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MovementReactiveRepository extends ReactiveCrudRepository<MovementEntity, Long> {

    MovementEntity findFirstByAccountId(Long id);

    @Query("SELECT * FROM challenge.movement m WHERE m.account_id = :accountId AND m.date BETWEEN :startDate AND :endDate")
    Flux<MovementEntity> findByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate);
}
