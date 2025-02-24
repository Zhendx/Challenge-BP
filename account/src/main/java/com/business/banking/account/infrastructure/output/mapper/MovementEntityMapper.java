package com.business.banking.account.infrastructure.output.mapper;

import com.business.banking.account.domain.*;
import com.business.banking.account.infrastructure.output.repository.entity.AccountEntity;
import com.business.banking.account.infrastructure.output.repository.entity.MovementEntity;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(
        componentModel = "spring"
)
public interface MovementEntityMapper {

    @Mapping(target = "type.code", source = "movementEntity.type")
    @Mapping(target = "type.description", source = "movementEntity.typeDescription")
    Movement toMovement(MovementEntity movementEntity);

    @Mapping(target = "type", source = "movement.type.code")
    @Mapping(target = "typeDescription", source = "movement.type.description")
    MovementEntity toMovementEntity(Movement movement);

    @Mapping(target = "type", source = "movement.type.code")
    @Mapping(target = "typeDescription", source = "movement.type.description")
    @Mapping(target = "movementId", source="movement.movementId")
    MovementEntity toMovementEntityPut(Movement movement);

    @Mapping(target = "number", source = "account.number")
    @Mapping(target = "type", source = "account.type.description")
    @Mapping(target = "balance", source = "movement.balance")
    @Mapping(target = "status", source = "account.status")
    @Mapping(target = "typeMovement", source = "movement.type.code")
    @Mapping(target = "value", source = "movement.value")
    MovementTransactionResponse movementToMovementTransactionResponse(Movement movement, Account account);

    @Mapping(target = "date", source = "movement.date")
    @Mapping(target = "name", source = "client.person.name")
    @Mapping(target = "typeAccount", source = "account.type.description")
    @Mapping(target = "number", source = "account.number")
    @Mapping(target = "typeMovement", source = "movement.type.code")
    @Mapping(target = "balance", source = "movement.balance")
    @Mapping(target = "status", source = "account.status")
    @Mapping(target = "value", source = "movement.value")
    @Mapping(target = "availableBalance", source = "movementReportResponse.availableBalance")
    MovementReportResponse toMovementReportResponse(Account account, Client client, Movement movement, MovementReportResponse movementReportResponse);
}
