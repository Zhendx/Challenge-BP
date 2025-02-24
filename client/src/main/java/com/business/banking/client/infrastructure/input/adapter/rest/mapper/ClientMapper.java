package com.business.banking.client.infrastructure.input.adapter.rest.mapper;

import com.business.banking.client.domain.PatchClientRequest;
import com.business.banking.client.domain.PutClientRequest;
import com.business.banking.client.infrastructure.input.adapter.rest.models.Client;
import com.business.banking.client.infrastructure.input.adapter.rest.models.GetClientByIdResponse;
import com.business.banking.client.infrastructure.input.adapter.rest.models.PostClientRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface ClientMapper {
    @Mapping(target = "person.gender.code", source = "client.person.gender.value")
    Client toClient(com.business.banking.client.domain.Client client);

    @Mapping(target = "person.gender", source = "client.person.gender.code")
    com.business.banking.client.domain.Client toClient(PostClientRequest client);

    @Mapping(target = "person.gender", source = "putClientRequest.person.gender.code")
    PutClientRequest toPutClientRequest(com.business.banking.client.infrastructure.input.adapter.rest.models.PutClientRequest putClientRequest);

    @Mapping(target = "person.gender.code", source = "client.person.gender.value")
    GetClientByIdResponse toGetClientByIdResponse(com.business.banking.client.domain.Client client);

    PatchClientRequest toPatchClientRequest(com.business.banking.client.infrastructure.input.adapter.rest.models.PatchClientRequest patchClientRequest);
}
