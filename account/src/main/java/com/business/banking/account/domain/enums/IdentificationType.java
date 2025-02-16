package com.business.banking.account.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdentificationType {
    CCPT("CCPT"),

    TXID("TXID"),

    IDCD("IDCD");

    public final String value;
}
