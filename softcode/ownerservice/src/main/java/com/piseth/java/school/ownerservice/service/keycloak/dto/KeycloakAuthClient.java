package com.piseth.java.school.ownerservice.service.keycloak.dto;

import com.piseth.java.school.ownerservice.dto.LoginResponse;

import reactor.core.publisher.Mono;

public interface KeycloakAuthClient {

    Mono<LoginResponse> login(String username, String password);
}