package com.pichincha.business.banking.services.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientResponseDTO implements Serializable {

    private String password;
    private Boolean state;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
}
