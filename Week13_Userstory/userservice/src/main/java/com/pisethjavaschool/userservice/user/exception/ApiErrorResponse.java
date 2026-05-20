package com.pisethjavaschool.userservice.user.exception;

import java.time.Instant;

public record ApiErrorResponse(
        Instant timestamp,
        String path,
        int status,
        String error,
        String message
) {
}