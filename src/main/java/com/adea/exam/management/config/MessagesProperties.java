package com.adea.exam.management.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@PropertySource("classpath:messages.properties")
@ConfigurationProperties(prefix = "message")
@Validated
@Data
public class MessagesProperties {

    @NotNull
    private String userExist;

    @NotNull
    private String clientExist;

    @NotNull
    private String userNotFound;
}
