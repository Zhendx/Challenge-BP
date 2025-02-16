package com.business.banking.account.domain;

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
public class Client {
    String clientId;
    String password;
    Boolean status;
    Person person;
}

