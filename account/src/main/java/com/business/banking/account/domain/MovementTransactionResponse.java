package com.business.banking.account.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class MovementTransactionResponse {
    String number;

    String type;

    BigDecimal balance;

    Boolean status;

    String typeMovement;

    BigDecimal value;
}
