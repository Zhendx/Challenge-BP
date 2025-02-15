package com.business.banking.client.domain.enums;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum GenderType {
    MALE("MALE"),
    FEMA("FEMA");

    public final String value;
}
