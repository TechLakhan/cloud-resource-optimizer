package com.cro.resource_monitoring_service.exception;

public class UnauthorizedOperationException extends Exception {
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
