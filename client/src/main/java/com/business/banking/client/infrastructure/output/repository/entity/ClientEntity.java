package com.business.banking.client.infrastructure.output.repository.entity;

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
@Table("challenge.client")
public class ClientEntity extends PersonEntity{
    @Id
    @Column("client_id")
    Long clientId;

    String password;
    Boolean status;
}
