package com.business.banking.account.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class PatchMovementRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate date;
    Type type;
}
