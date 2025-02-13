package com.pichincha.business.banking.services.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client extends Person{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String password;
    private Boolean state;
}
