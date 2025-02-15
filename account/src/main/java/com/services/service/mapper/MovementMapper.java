package com.pichincha.business.banking.services.service.mapper;

import com.pichincha.business.banking.services.service.dto.MovementRequestDTO;
import com.pichincha.business.banking.services.service.dto.MovementResponseDTO;
import com.pichincha.business.banking.services.service.dto.MovementTransactionResponseDTO;
import com.pichincha.business.banking.services.service.entity.Account;
import com.pichincha.business.banking.services.service.entity.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovementMapper {
    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    MovementResponseDTO movementToMovementResponseDTO(Movement movement);

    @Mapping(target = "id", ignore = true)
    Movement movementRequestDTOToMovement(MovementRequestDTO movementRequestDTO);

    @Mapping(target = "number", source = "account.number")
    @Mapping(target = "type", source = "account.type")
    @Mapping(target = "balance", source = "movement.balance")
    @Mapping(target = "state", source = "account.state")
    @Mapping(target = "typeMovement", source = "movement.type")
    @Mapping(target = "value", source = "movement.value")
    MovementTransactionResponseDTO movementToMovementTransactionResponseDTO(Movement movement, Account account);

}
