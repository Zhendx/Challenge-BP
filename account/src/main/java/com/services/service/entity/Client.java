package com.pichincha.business.banking.services.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String password;
    private Boolean state;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
