package com.business.banking.client.util;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "test.clients")
@Data
@Validated
public class PorpertiesTest {

    @Valid
    private Endpoint client;

    @Data
    @Validated
    public static final class Endpoint {
        @NotBlank
        private String path;
    }
}
