package com.pichincha.business.banking.services.util;

import com.pichincha.business.banking.services.service.dto.ClientRequestDTO;
import com.pichincha.business.banking.services.service.dto.ClientResponseDTO;
import com.pichincha.business.banking.services.service.entity.Client;


public class MockObjects {

    private MockObjects(){}

    public static ClientRequestDTO buildClientRequestDTO(){
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setAge(25);
        clientRequestDTO.setIdentification("ID123");
        clientRequestDTO.setName("Test Client");
        return clientRequestDTO;
    }

    public static Client buildClient(){
        Client client = new Client();
        client.setAge(25);
        client.setIdentification("ID123");
        client.setName("Test Client");
        return client;
    }

    public static ClientResponseDTO buildClientResponseDTO(){
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setAge(25);
        clientResponseDTO.setIdentification("ID123");
        clientResponseDTO.setName("Test Client");
        return clientResponseDTO;
    }
}
