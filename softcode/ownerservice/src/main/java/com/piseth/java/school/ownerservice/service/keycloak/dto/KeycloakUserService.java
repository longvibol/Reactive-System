package com.piseth.java.school.ownerservice.service.keycloak.dto;

import reactor.core.publisher.Mono;

public interface KeycloakUserService {
	Mono<String> getAdminToken();

//	Mono<CreateKeycloakUserResponse> createUser(CreateKeycloakUserRequest request);
//
//	Mono<Void> resetPassword(ResetPasswordRequest request);
//
//	Mono<LoginResponse> login(LoginRequest request);
}