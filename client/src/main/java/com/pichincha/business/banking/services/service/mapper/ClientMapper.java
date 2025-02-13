package com.pichincha.business.banking.services.service.mapper;

import com.pichincha.business.banking.services.service.dto.ClientRequestDTO;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import com.pichincha.business.banking.services.service.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientResponseDTO clientToClientResponseDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client clientRequestDTOToClient(ClientRequestDTO clientRequestDTO);

}
