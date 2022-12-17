package com.example.NewLibrary17.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistingAuthorException extends RuntimeException {
    public AlreadyExistingAuthorException(String message) {
        super(message);
    }
}
