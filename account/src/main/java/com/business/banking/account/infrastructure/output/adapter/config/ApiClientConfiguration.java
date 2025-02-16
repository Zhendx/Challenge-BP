package com.business.banking.account.infrastructure.output.adapter.config;

import com.business.banking.account.infrastructure.output.adapter.rest.spClient.client.ClientApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApiClientConfiguration {
    private final ApiClientProperties apiClientProperties;

    @Bean
    @NonNull
    public ClientApi clientApi() {
        final var clientApi = new ClientApi();
        clientApi.getApiClient()
                .setBasePath(apiClientProperties.getClientSpClient().getBaseUrl());
        return clientApi;
    }
}
