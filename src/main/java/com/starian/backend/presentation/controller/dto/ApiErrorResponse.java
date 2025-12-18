package com.starian.backend.presentation.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        int status,
        String path,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'-'HH:mm:ss")
        LocalDateTime timeStamp
) {
}