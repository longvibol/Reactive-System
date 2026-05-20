package com.pisethjavaschool.userservice.user.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pisethjavaschool.userservice.user.domain.PinResetTokenData;
import com.pisethjavaschool.userservice.user.domain.enumeration.UserType;

import reactor.core.publisher.Mono;

@Service
public class PinResetTokenService {

    private static final Duration TOKEN_TTL = Duration.ofMinutes(10);

    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, TokenValue> tokens = new ConcurrentHashMap<>();

    public Mono<String> createToken(String countryCode, String phoneNumber, UserType userType) {
        cleanupExpiredTokens();

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        tokens.put(token, new TokenValue(
                countryCode,
                phoneNumber,
                userType,
                Instant.now().plus(TOKEN_TTL)
        ));

        return Mono.just(token);
    }

    public Mono<PinResetTokenData> validateToken(String token) {
        cleanupExpiredTokens();

        TokenValue value = tokens.get(token);

        if (value == null || value.expiresAt().isBefore(Instant.now())) {
            tokens.remove(token);
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid or expired reset token"
            ));
        }

        return Mono.just(new PinResetTokenData(
                value.countryCode(),
                value.phoneNumber(),
                value.userType()
        ));
    }

    public Mono<Void> consumeToken(String token) {
        tokens.remove(token);
        return Mono.empty();
    }

    private void cleanupExpiredTokens() {
        Instant now = Instant.now();
        tokens.entrySet().removeIf(entry -> entry.getValue().expiresAt().isBefore(now));
    }

    private record TokenValue(
            String countryCode,
            String phoneNumber,
            UserType userType,
            Instant expiresAt
    ) {
    }
}