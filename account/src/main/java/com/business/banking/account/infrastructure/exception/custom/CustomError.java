package com.business.banking.account.infrastructure.exception.custom;

import com.business.banking.account.infrastructure.exception.ErrorApp;

public class CustomError {
    public static final ErrorApp EmptyDataException = new ErrorApp("CE001","The list is empty.");
    public static final ErrorApp NotFoundDataException = new ErrorApp("CE002","The account is not found.");
    public static final ErrorApp ApiClientException = new ErrorApp("CE003","Error in DataBase Client.");
    public static final ErrorApp DeleteException = new ErrorApp("CE004","Cannot be deleted as it has dependencies.");
    public static final ErrorApp RetreatException = new ErrorApp("CE005","The balance is less than the amount you want to withdraw.");
    public static final ErrorApp BalanceException = new ErrorApp("CE006","The entered value is not valid.");
}
