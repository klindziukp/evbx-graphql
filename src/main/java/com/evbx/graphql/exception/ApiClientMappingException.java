package com.evbx.graphql.exception;

public class ApiClientMappingException extends Exception {

    public ApiClientMappingException(String message) {
        super(message);
    }

    public ApiClientMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
