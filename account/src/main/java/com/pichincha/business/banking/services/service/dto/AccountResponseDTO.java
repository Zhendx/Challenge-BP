package com.pichincha.business.banking.services.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AccountResponseDTO implements Serializable {

    private String number;
    private String type;
    private double balance;
    private Boolean state;
    private int idClient;

}
