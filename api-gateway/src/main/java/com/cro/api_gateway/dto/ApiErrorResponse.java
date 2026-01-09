package com.cro.api_gateway.dto;

import java.time.Instant;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant time
) {}
