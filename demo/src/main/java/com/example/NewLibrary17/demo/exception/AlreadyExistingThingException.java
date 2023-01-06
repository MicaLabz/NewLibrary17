package com.example.NewLibrary17.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistingThingException extends RuntimeException {
    public AlreadyExistingThingException(String message) {
        super(message);
    }
}
