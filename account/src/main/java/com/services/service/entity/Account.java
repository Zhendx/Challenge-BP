package com.pichincha.business.banking.services.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String number;
    private String type;
    private double balance;
    private Boolean state;
    @Column(name="id_client")
    private int idClient;
}
