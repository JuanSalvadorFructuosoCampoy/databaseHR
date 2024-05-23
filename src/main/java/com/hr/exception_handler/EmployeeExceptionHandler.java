package com.hr.exception_handler;

import com.hr.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<Map<String,String>> handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("employee", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DepartamentoNoEncontradoException.class)
    public ResponseEntity<Map<String,String>> handleDepartamentoNoEncontradoException(DepartamentoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("department", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ManagerNoEncontradoException.class)
    public ResponseEntity<Map<String,String>> handleManagerNoEncontradoException(ManagerNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("manager", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TrabajoNoEncontradoException.class)
    public ResponseEntity<Map<String,String>> handleTrabajoNoEncontradoException(TrabajoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("job", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Map<String,String>> handleEmailDuplicadoException(EmailDuplicadoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("email", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MultipleException.class)
    public ResponseEntity<Map<String,String>> handleMultipleExceptions(MultipleException e) {
        Map<String, String> errores = new HashMap<>();
        for (Exception exception : e.getExceptions()) {
            if (exception instanceof EmpleadoNoEncontradoException) {
                errores.put("employee", exception.getMessage());
            } else if (exception instanceof DepartamentoNoEncontradoException) {
                errores.put("department", exception.getMessage());
            } else if (exception instanceof ManagerNoEncontradoException) {
                errores.put("manager", exception.getMessage());
            } else if (exception instanceof TrabajoNoEncontradoException) {
                errores.put("job", exception.getMessage());
            } else if (exception instanceof EmailDuplicadoException) {
                errores.put("email", exception.getMessage());
            }
        }
        return ResponseEntity.badRequest().body(errores);
    }
}
