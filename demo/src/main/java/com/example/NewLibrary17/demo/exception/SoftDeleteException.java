package com.example.NewLibrary17.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SoftDeleteException extends RuntimeException {

    public SoftDeleteException(String message){
        super(message);
    }
}
