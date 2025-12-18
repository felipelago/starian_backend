package com.starian.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final String clientMessage;

    public BusinessException(String clientMessage) {
        this(HttpStatus.BAD_REQUEST, clientMessage, null);
    }

    public BusinessException(HttpStatus status, String clientMessage) {
        this(status, clientMessage, null);
    }

    public BusinessException(HttpStatus status, String clientMessage, Throwable cause) {
        super(clientMessage, cause);
        this.status = status;
        this.clientMessage = clientMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}