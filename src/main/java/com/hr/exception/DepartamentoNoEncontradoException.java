package com.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartamentoNoEncontradoException extends Exception{
    public DepartamentoNoEncontradoException(String msg) {
        super(msg);
    }
}
