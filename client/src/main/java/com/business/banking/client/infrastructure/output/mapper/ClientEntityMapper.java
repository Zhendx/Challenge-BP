package com.business.banking.client.infrastructure.output.mapper;

import com.business.banking.client.domain.Client;
import com.business.banking.client.domain.enums.GenderType;
import com.business.banking.client.infrastructure.output.repository.entity.ClientEntity;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(
        componentModel = "spring"
)
public interface ClientEntityMapper {

    @Mapping(target = "person.gender", source="clientEntity.gender", qualifiedByName = "typeGender")
    @Mapping(target = "person.name", source="clientEntity.name")
    @Mapping(target = "person.age", source="clientEntity.age")
    @Mapping(target = "person.address.value", source="clientEntity.address")
    @Mapping(target = "person.address.type", source="clientEntity.typeAddress")
    @Mapping(target = "person.phoneAddress.type", source="clientEntity.typePhoneAddress")
    @Mapping(target = "person.phoneAddress.description", source="clientEntity.phoneAddress")
    @Mapping(target = "person.identification.type.code", source="clientEntity.typeIdentification")
    @Mapping(target = "person.identification.type.name", source="clientEntity.typeIdentification", qualifiedByName = "typeName")
    @Mapping(target = "person.identification.identifier.value", source="clientEntity.identification")
    Client toClient(ClientEntity clientEntity);

    @Mapping(target = "gender", source = "client.person.gender")
    @Mapping(target = "name", source = "client.person.name")
    @Mapping(target = "age", source = "client.person.age")
    @Mapping(target = "address", source = "client.person.address.value")
    @Mapping(target = "typeAddress", source = "client.person.address.type")
    @Mapping(target = "typePhoneAddress", source = "client.person.phoneAddress.type")
    @Mapping(target = "phoneAddress", source = "client.person.phoneAddress.description")
    @Mapping(target = "typeIdentification", source = "client.person.identification.type.code")
    @Mapping(target = "identification", source = "client.person.identification.identifier.value")
    ClientEntity toClientEntity(Client client);

    @Mapping(target = "clientId", source = "client.clientId")
    @Mapping(target = "gender", source = "client.person.gender")
    @Mapping(target = "name", source = "client.person.name")
    @Mapping(target = "age", source = "client.person.age")
    @Mapping(target = "address", source = "client.person.address.value")
    @Mapping(target = "typeAddress", source = "client.person.address.type")
    @Mapping(target = "typePhoneAddress", source = "client.person.phoneAddress.type")
    @Mapping(target = "phoneAddress", source = "client.person.phoneAddress.description")
    @Mapping(target = "typeIdentification", source = "client.person.identification.type.code")
    @Mapping(target = "identification", source = "client.person.identification.identifier.value")
    ClientEntity toClientEntityPut(Client client);

    @Named("typeName")
    default String typeName(String code){
        return Objects.equals(code, "TXID") ? "Ruc" : Objects.equals(code, "IDCD") ? "Cedula" : "Pasaporte";
    }

    @Named("typeGender")
    default GenderType typeGender(String code){
        return GenderType.valueOf(code);
    }
}
