package com.business.banking.account.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class PutMovementRequest {
    LocalDate date;
    Type type;
    BigDecimal value;
    BigDecimal balance;
    String accountId;
}
