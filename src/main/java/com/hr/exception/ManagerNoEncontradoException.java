package com.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ManagerNoEncontradoException extends Exception {
    public ManagerNoEncontradoException(String msg) {
        super(msg);
    }
}
