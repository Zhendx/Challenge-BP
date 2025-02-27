package com.business.banking.client.infrastructure.exception.resolver;

import com.business.banking.client.infrastructure.exception.AppException;
import com.business.banking.client.infrastructure.exception.ErrorApp;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class HandlerController {
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorApp deniedPermissionException(NullPointerException ex) {
        return new ErrorApp("001","Error has occurred information not found");
    }

    @ExceptionHandler(value = {AppException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApp AppException(AppException ex) {
        return ex.getError();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolation(RuntimeException ex) {
        return "Los parametros ingresados se encuentran incorrectos";
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Exception ex) {
        return "Internal error :)(: ";
    }
}
