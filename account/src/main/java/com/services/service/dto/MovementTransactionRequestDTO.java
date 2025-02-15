package com.pichincha.business.banking.services.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementTransactionRequestDTO {
    private String type;
    private double value;
}
