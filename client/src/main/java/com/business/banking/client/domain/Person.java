package com.business.banking.client.domain;

import com.business.banking.client.domain.enums.GenderType;
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
    GenderType gender;
    Integer age;
    PersonIdentification identification;
    Address address;
    PhoneAddress phoneAddress;
}
