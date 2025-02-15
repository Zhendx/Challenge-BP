package com.pichincha.business.banking.services.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementTransactionResponseDTO {
    private String number;
    private String type;
    private Double balance;
    private Boolean state;
    private String typeMovement;
    private Double value;
}
