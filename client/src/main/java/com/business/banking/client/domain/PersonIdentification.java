package com.business.banking.client.domain;

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
public class PersonIdentification {
    TypeOfIdentification type;
    Identifier identifier;
}
