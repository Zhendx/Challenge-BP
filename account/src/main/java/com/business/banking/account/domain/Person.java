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
public class Person {
    String name;
    String gender;
    Integer age;
    PersonIdentification identification;
    Address address;
    PhoneAddress phoneAddress;
}
