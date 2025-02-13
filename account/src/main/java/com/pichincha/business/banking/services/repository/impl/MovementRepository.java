package com.pichincha.business.banking.services.repository.impl;

import com.pichincha.business.banking.services.service.dto.MovementReportResponseDTO;
import com.pichincha.business.banking.services.service.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    Movement findFirstByIdAccount(Long id);

    @Modifying
    @Transactional
    @Query(value = "SELECT new com.pichincha.business.banking.services.service.dto.MovementReportResponseDTO(m.date, c.name, a.type, a.number, m.type, m.balance, a.state, m.value, a.balance) " +
            "FROM Movement m " +
            "JOIN Account a ON m.idAccount = a.id " +
            "JOIN Client c ON a.idClient = c.id " +
            "WHERE a.number = ?1 AND " +
            "m.date BETWEEN ?2 AND ?3")
    List<MovementReportResponseDTO> findByDateInitialAndDateFinalAndIdentification(String id, Date dateInitial, Date dateFinal);
}
