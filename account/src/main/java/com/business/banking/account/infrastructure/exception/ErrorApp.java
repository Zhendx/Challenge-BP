package com.business.banking.account.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorApp {
    private String code;
    private String message;
}
