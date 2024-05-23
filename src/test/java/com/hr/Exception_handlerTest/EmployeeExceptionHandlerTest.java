package com.hr.Exception_handlerTest;
import com.hr.exception.*;
import com.hr.exception_handler.EmployeeExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeExceptionHandlerTest {

    private final EmployeeExceptionHandler exceptionHandler = new EmployeeExceptionHandler();

    @Test
    void testHandleEmpleadoNoEncontradoException() {
        EmpleadoNoEncontradoException exception = new EmpleadoNoEncontradoException("Empleado no encontrado");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleEmpleadoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Empleado no encontrado", response.getBody().get("employee"));
    }

    @Test
    void testHandleDepartamentoNoEncontradoException() {
        DepartamentoNoEncontradoException exception = new DepartamentoNoEncontradoException("Departamento no encontrado");

        ResponseEntity<Map<String,String>> response = exceptionHandler.handleDepartamentoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Departamento no encontrado", response.getBody().get("department"));
    }

    @Test
    void testHandleManagerNoEncontradoException() {
        ManagerNoEncontradoException exception = new ManagerNoEncontradoException("Manager no encontrado");

        ResponseEntity<Map<String,String>> response = exceptionHandler.handleManagerNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Manager no encontrado", response.getBody().get("manager"));
    }

    @Test
    void testHandleTrabajoNoEncontradoException() {
        TrabajoNoEncontradoException exception = new TrabajoNoEncontradoException("Trabajo no encontrado");

        ResponseEntity<Map<String,String>> response = exceptionHandler.handleTrabajoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trabajo no encontrado", response.getBody().get("job"));
    }

    @Test
    void testHandleEmailDuplicadoException() {
        EmailDuplicadoException exception = new EmailDuplicadoException("Email duplicado");

        ResponseEntity<Map<String,String>> response = exceptionHandler.handleEmailDuplicadoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email duplicado", response.getBody().get("email"));
    }


    @Test
    void testHandleMultipleExceptions() {
        // Crear una MultipleException con varias excepciones diferentes
        MultipleException multipleException = new MultipleException();
        multipleException.addException(new EmpleadoNoEncontradoException("Empleado no encontrado"));
        multipleException.addException(new DepartamentoNoEncontradoException("Departamento no encontrado"));
        multipleException.addException(new ManagerNoEncontradoException("Manager no encontrado"));
        multipleException.addException(new TrabajoNoEncontradoException("Trabajo no encontrado"));
        multipleException.addException(new EmailDuplicadoException("Email duplicado"));

        // Llamar al método handleMultipleExceptions con la MultipleException creada
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleMultipleExceptions(multipleException);

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con el código de estado HTTP correcto
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con los mensajes de error correctos
        Map<String, String> errores = response.getBody();
        assertEquals("Empleado no encontrado", errores.get("employee"));
        assertEquals("Departamento no encontrado", errores.get("department"));
        assertEquals("Manager no encontrado", errores.get("manager"));
        assertEquals("Trabajo no encontrado", errores.get("job"));
        assertEquals("Email duplicado", errores.get("email"));
    }
}
