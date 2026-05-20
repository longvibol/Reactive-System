package com.pisethjavaschool.userservice.user.dto;

import java.time.Instant;

public record ApiErrorResponse(
        Instant timestamp,
        String path,
        int status,
        String error,
        String message
) {
}