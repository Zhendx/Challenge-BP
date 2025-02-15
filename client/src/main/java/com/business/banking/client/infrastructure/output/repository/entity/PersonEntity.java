package com.business.banking.client.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class PersonEntity {
    String name;
    String gender;
    Integer age;
    String identification;
    @Column("type_identification")
    String typeIdentification;
    String address;
    @Column("type_address")
    String typeAddress;
    @Column("phone_address")
    String phoneAddress;
    @Column("type_phone_address")
    String typePhoneAddress;
}
