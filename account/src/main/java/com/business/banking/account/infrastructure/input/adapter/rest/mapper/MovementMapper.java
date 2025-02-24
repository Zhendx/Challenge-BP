package com.business.banking.account.infrastructure.input.adapter.rest.mapper;

import com.business.banking.account.domain.PatchMovementRequest;
import com.business.banking.account.domain.PostMovementTransactionRequest;
import com.business.banking.account.domain.PutMovementRequest;
import com.business.banking.account.infrastructure.input.adapter.rest.models.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface MovementMapper {

    Movement toMovement(com.business.banking.account.domain.Movement movement);

    com.business.banking.account.domain.Movement toMovement(PostMovementRequest movement);

    GetAccountMovementByIdResponse toGetMovementByIdResponse(com.business.banking.account.domain.Movement movement);

    PatchMovementRequest toPatchMovementRequest(PatchAccountMovementRequest patchAccountMovementRequest);

    PutMovementRequest toPutMovementRequest(PutAccountMovementRequest putAccountMovementRequest);

    MovementReportResponse toMovementReportResponse(com.business.banking.account.domain.MovementReportResponse movementReportResponse);

    PostMovementTransactionRequest toPostMovementTransactionRequest(PostAccountMovementTransactionRequest postAccountMovementTransactionRequest);

    PostAccountMovementTransactionResponse toPostAccountMovementTransactionResponse(com.business.banking.account.domain.MovementTransactionResponse movementTransactionResponse);
}
