package com.pichincha.business.banking.services.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "movement")
public class Movement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private Date date;
    private String type;
    private double value;
    private double balance;
    @Column(name="id_account")
    private int idAccount;
}
