package com.piseth.java.school.ownerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.ownerservice.service.keycloak.dto.KeycloakUserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keycloak/users")
public class KeycloakUserController {
	
	private final KeycloakUserService service;
	
	@GetMapping("/admin-token")
    public Mono<String> getAdminToken() {
        return service.getAdminToken();
    }

}
