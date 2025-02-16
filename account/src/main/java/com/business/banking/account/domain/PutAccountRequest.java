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
public class PutAccountRequest {
    String number;

    Type type;

    BigDecimal balance;

    Boolean status;

    String clientId;
}
