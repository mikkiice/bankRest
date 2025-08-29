package com.example.bankcards.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {
    private String issuer;
    private String secret;
    private String accessTtl;
    private String refreshTtl;
    private String header;
    private String prefix;

}
