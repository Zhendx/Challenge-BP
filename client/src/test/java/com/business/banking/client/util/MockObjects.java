package com.business.banking.client.util;

import com.business.banking.client.domain.*;
import com.business.banking.client.domain.enums.GenderType;
import com.business.banking.client.domain.enums.IdentificationType;
import com.business.banking.client.infrastructure.input.adapter.rest.models.PostClientRequest;
import com.business.banking.client.infrastructure.output.repository.entity.*;

public class MockObjects {

    public static Client buildClient(){
        return Client.builder()
                .clientId("1")
                .password("assd1654")
                .person(Person.builder()
                        .address(Address.builder()
                                .type("principal")
                                .value("marcelaniado").build())
                        .age(15)
                        .name("Jhon doe")
                        .gender(GenderType.MALE)
                        .identification(PersonIdentification.builder()
                                .type(TypeOfIdentification.builder()
                                        .code(IdentificationType.IDCD)
                                        .name("Cdula").build())
                                .identifier(Identifier.builder()
                                        .value("05445865459").build()).build()).build())
                .status(true).build();
    }

    public static ClientEntity buildClientEntity(){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(1L);
        clientEntity.setPassword("Holamundo");
        clientEntity.setStatus(false);
        clientEntity.setAge(12);
        clientEntity.setName("Jhon doe");
        clientEntity.setGender("MALE");
        clientEntity.setAddress("Junin");
        clientEntity.setPhoneAddress("09854569856");
        return clientEntity;
    }

    public static PutClientRequest buildPutClientRequest(){
        return PutClientRequest.builder()
                .person(Person.builder()
                        .address(Address.builder()
                                .type("principal")
                                .value("marcelaniado").build())
                        .age(15)
                        .name("Jhon doe")
                        .gender(GenderType.MALE)
                        .identification(PersonIdentification.builder()
                                .type(TypeOfIdentification.builder()
                                        .code(IdentificationType.IDCD)
                                        .name("Cdula").build())
                                .identifier(Identifier.builder()
                                        .value("05445865459").build()).build()).build())
                .status(true).build();
    }

    public static com.business.banking.client.infrastructure.input.adapter.rest.models.Client buildClientResponse(){
        com.business.banking.client.infrastructure.input.adapter.rest.models.Client client = new com.business.banking.client.infrastructure.input.adapter.rest.models.Client();
        client.setClientId("1");
        client.setPassword("Holamundo");
        client.setStatus(false);
        client.setPerson(new com.business.banking.client.infrastructure.input.adapter.rest.models.Person());
        return client;
    }

    public static PostClientRequest buildPostClientRequest(){
        PostClientRequest postClientRequest = new PostClientRequest();
        postClientRequest.setClientId("1");
        postClientRequest.setPassword("Hola123");
        postClientRequest.setStatus(true);
        postClientRequest.setPerson(new com.business.banking.client.infrastructure.input.adapter.rest.models.Person());
        return postClientRequest;
    }
}
