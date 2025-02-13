package com.pichincha.business.banking.services.exception.custom;

import com.pichincha.business.banking.services.exception.ErrorApp;

public class CustomError {
    public static final ErrorApp EmptyDataException = new ErrorApp("CE001","The list is empty.");
    public static final ErrorApp NotFoundDataException = new ErrorApp("CE002","The client is not found.");
    public static final ErrorApp ApiClientException = new ErrorApp("CE003","Error in DataBase Client.");
}
