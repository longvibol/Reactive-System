package com.piseth.java.school.ownerservice.service.impl;

import org.springframework.stereotype.Service;

import com.piseth.java.school.ownerservice.service.keycloak.KeycloakAdminClient;
import com.piseth.java.school.ownerservice.service.keycloak.dto.KeycloakAuthClient;
import com.piseth.java.school.ownerservice.service.keycloak.dto.KeycloakUserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
	
	private final KeycloakAdminClient keycloakAdminClient;
//	private final KeycloakAuthClient keycloakAuthClient;

	@Override
	public Mono<String> getAdminToken() {
		return keycloakAdminClient.getAdminToken();
	}

}
