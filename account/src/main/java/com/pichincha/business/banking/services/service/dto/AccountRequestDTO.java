package com.pichincha.business.banking.services.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO implements Serializable {

    private String number;
    private String type;
    private double balance;
    private Boolean state;
    private int idClient;


}
