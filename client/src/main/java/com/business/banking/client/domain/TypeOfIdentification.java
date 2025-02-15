package com.business.banking.client.domain;

import com.business.banking.client.domain.enums.IdentificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class TypeOfIdentification {
    IdentificationType code;
    String name;
}
