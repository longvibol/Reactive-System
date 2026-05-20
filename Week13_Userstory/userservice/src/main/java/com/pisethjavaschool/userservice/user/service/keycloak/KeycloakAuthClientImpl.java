package com.pisethjavaschool.userservice.user.service.keycloak;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisethjavaschool.userservice.config.KeycloakProperties;
import com.pisethjavaschool.userservice.user.dto.LoginResponse;
import com.pisethjavaschool.userservice.user.util.LogMasker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakAuthClientImpl implements KeycloakAuthClient {

    private final WebClient keycloakWebClient;
    private final KeycloakProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<LoginResponse> login(String username, String pin) {
        log.info("Calling Keycloak login. username={}", LogMasker.maskPhone(username));

        return keycloakWebClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", properties.realm())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", properties.client().clientId())
                        .with("client_secret", properties.client().clientSecret())
                        .with("username", username)
                        .with("password", pin))
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> handleLoginResponse(response.statusCode().isError(), body))
                );
    }

    private Mono<LoginResponse> handleLoginResponse(boolean hasError, String body) {
        if (hasError) {
            String keycloakError = getKeycloakError(body, "error");
            String keycloakErrorDescription = getKeycloakError(body, "error_description");

            log.warn(
                    "Keycloak login failed. error={}, description={}",
                    keycloakError,
                    keycloakErrorDescription
            );

            if ("invalid_grant".equals(keycloakError)) {
                return Mono.error(new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid phone number or PIN"
                ));
            }

            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Login failed. Please check your phone number and PIN."
            ));
        }

        return toLoginResponse(body);
    }

    private Mono<LoginResponse> toLoginResponse(String body) {
        try {
            Map<?, ?> response = objectMapper.readValue(body, Map.class);

            return Mono.just(new LoginResponse(
                    getString(response, "access_token"),
                    getString(response, "refresh_token"),
                    getLong(response, "expires_in"),
                    getString(response, "token_type")
            ));
        } catch (Exception ex) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to process login response"
            ));
        }
    }

    private String getKeycloakError(String body, String key) {
        try {
            Map<?, ?> response = objectMapper.readValue(body, Map.class);
            return getString(response, key);
        } catch (Exception ex) {
            return null;
        }
    }

    private String getString(Map<?, ?> response, String key) {
        Object value = response.get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    private Long getLong(Map<?, ?> response, String key) {
        Object value = response.get(key);

        if (value instanceof Number number) {
            return number.longValue();
        }

        if (value != null) {
            return Long.parseLong(value.toString());
        }

        return null;
    }
}