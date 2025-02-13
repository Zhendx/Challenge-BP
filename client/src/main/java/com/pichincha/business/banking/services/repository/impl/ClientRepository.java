package com.pichincha.business.banking.services.repository.impl;

import com.pichincha.business.banking.services.service.entity.Client;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findFirstByIdentification(String identification);

    @Modifying
    @Transactional
    @Query("DELETE FROM Client c WHERE c.identification = ?1")
    void deleteByIdentification(String identification);


}
