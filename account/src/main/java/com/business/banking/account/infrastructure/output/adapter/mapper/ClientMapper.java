package com.business.banking.account.infrastructure.output.adapter.mapper;

import com.business.banking.account.domain.Client;
import com.business.banking.account.infrastructure.output.adapter.rest.spClient.bean.Gender;
import com.business.banking.account.infrastructure.output.adapter.rest.spClient.bean.GetClientByIdResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ClientMapper {

    @Mapping(target="person.gender", source="getClientByIdResponse.person.gender.code")
    @Mapping(target="person.phoneAddress.type", source="getClientByIdResponse.person.phoneAddress.type")
    Client toGetClientByIdResponse(GetClientByIdResponse getClientByIdResponse);
}
