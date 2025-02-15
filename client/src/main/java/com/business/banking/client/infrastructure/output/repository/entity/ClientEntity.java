package com.business.banking.client.infrastructure.output.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
@Entity
@Table("challenge.client")
public class ClientEntity extends PersonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("client_id")
    Long clientId;

    String password;
    Boolean status;
}
