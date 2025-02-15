package com.business.banking.client.infrastructure.output.repository;

import com.business.banking.client.infrastructure.output.repository.entity.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReactiveRepository extends ReactiveCrudRepository<ClientEntity, Long> {

}
