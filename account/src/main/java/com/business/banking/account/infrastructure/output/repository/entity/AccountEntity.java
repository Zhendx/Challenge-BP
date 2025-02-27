package com.business.banking.account.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
@Table("challenge.account")
public class AccountEntity {
    @Id
    @Column("account_id")
    Long accountId;

    String number;
    String type;
    BigDecimal balance;
    Boolean status;
    @Column("client_id")
    Long clientId;
}
