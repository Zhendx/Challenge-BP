package com.business.banking.account.infrastructure.output.repository.entity;

import com.business.banking.account.domain.Type;
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
public class PutAccount {
    String number;

    Type type;

    BigDecimal balance;

    Boolean status;

    String clientId;
}
