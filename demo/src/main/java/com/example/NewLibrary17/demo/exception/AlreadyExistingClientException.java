package com.example.NewLibrary17.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistingClientException extends RuntimeException {
    public AlreadyExistingClientException(String message) {
        super(message);
    }
}
