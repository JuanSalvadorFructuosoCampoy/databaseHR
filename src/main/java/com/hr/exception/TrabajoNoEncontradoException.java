package com.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TrabajoNoEncontradoException extends Exception{
    public TrabajoNoEncontradoException(String msg) {
        super(msg);
    }
}
