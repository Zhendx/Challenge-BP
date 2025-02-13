package com.pichincha.business.banking.services.repository.impl;

import com.pichincha.business.banking.services.service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findFirstByNumber(String number);

    @Modifying
    @Transactional
    @Query("DELETE FROM Account a WHERE a.number = ?1")
    void deleteByNumber(String number);

}
