package com.pichincha.business.banking.services.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDTO implements Serializable {

    private Date date;
    private String type;
    private double value;
    private double balance;

    private int idAccount;
}
