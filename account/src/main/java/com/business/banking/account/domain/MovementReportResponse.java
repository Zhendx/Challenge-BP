package com.business.banking.account.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

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
public class MovementReportResponse {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate date;

    String name;

    String typeAccount;

    String number;

    String typeMovement;

    BigDecimal balance;

    Boolean status;

    BigDecimal value;

    BigDecimal availableBalance;
}
