package com.pisethjavaschool.userservice.user.session;

import java.time.Instant;

@FunctionalInterface
public interface SessionExpirationReader<T> {

	// we catch the section time and count
    Instant expiresAt(T session);
}