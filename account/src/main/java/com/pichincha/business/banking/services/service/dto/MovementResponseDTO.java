package com.pichincha.business.banking.services.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MovementResponseDTO implements Serializable {

    private Date date;
    private String type;
    private double value;
    private double balance;

    private int idAccount;
}
