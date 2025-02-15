package com.pichincha.business.banking.services.service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MovementReportResponseDTO {

    private Date date;
    private String name;

    @Column(name="type")
    private String typeAccount;

    private String number;

    @Column(name="type")
    private String typeMovement;

    private Double balance;

    private Boolean state;
    private Double value;

    @Column(name="balance")
    private Double availableBalance;
}
