package com.business.banking.account.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated

@Table("challenge.movement")
public class MovementEntity {
    @Id
    @Column("movement_id")
    Long movementId;

    LocalDate date;
    String type;
    @Column("type_description")
    String typeDescription;

    BigDecimal value;
    BigDecimal balance;
    @Column("account_id")
    Long accountId;
}
