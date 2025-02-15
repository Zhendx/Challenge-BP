package com.business.banking.client.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhoneType {
    PHONE_NUMBER("PhoneNumber"),
    FAX_NUMBER("FaxNumber"),
    MOBILE_NUMBER("MobileNumber");

    public final String value;
}
