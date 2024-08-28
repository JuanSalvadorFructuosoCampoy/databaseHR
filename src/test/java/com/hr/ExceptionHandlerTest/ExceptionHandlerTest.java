package com.hr.ExceptionHandlerTest;

import com.hr.exception.*;
import com.hr.exceptionhandler.ExcepcionesHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerTest {

    private final ExcepcionesHandler excepcionesHandler = new ExcepcionesHandler();

    @Test
    void testHandleEmpleadoNoEncontradoException() {
        EmpleadoNoEncontradoException exception = new EmpleadoNoEncontradoException("Empleado no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleEmpleadoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Empleado no encontrado", response.getBody().get("employee"));
    }

    @Test
    void testHandleDepartamentoNoEncontradoException() {
        DepartamentoNoEncontradoException exception = new DepartamentoNoEncontradoException("Departamento no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleDepartamentoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Departamento no encontrado", response.getBody().get("department"));
    }

    @Test
    void testHandleManagerNoEncontradoException() {
        ManagerNoEncontradoException exception = new ManagerNoEncontradoException("Manager no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleManagerNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Manager no encontrado", response.getBody().get("manager"));
    }

    @Test
    void testHandleTrabajoNoEncontradoException() {
        TrabajoNoEncontradoException exception = new TrabajoNoEncontradoException("Trabajo no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleTrabajoNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trabajo no encontrado", response.getBody().get("job"));
    }

    @Test
    void testHandleLocationNoEncontradoException() {
        LocationNoEncontradoException exception = new LocationNoEncontradoException("Location no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleLocationNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Location no encontrado", response.getBody().get("location"));
    }

    @Test
    void testHandleRegionNoEncontradoException() {
        RegionNoEncontradoException exception = new RegionNoEncontradoException("Region no encontrada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleRegionNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Region no encontrada", response.getBody().get("region"));
    }

    @Test
    void testHandleEmailNoEncontradoException() {
        EmailNoEncontradoException exception = new EmailNoEncontradoException("Email no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleEmailNoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Email no encontrado", response.getBody().get("email"));
    }

    @Test
    void testHandleEmailDuplicadoException() {
        EmailDuplicadoException exception = new EmailDuplicadoException("Email duplicado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleEmailDuplicadoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email duplicado", response.getBody().get("email"));
    }

    @Test
    void testHandleRegionDuplicadoException() {
        RegionDuplicadoException exception = new RegionDuplicadoException("Región duplicada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleRegionDuplicadoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Región duplicada", response.getBody().get("region"));
    }

    @Test
    void testHandleTrabajoDuplicadoException() {
        TrabajoDuplicadoException exception = new TrabajoDuplicadoException("Trabajo duplicado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleTrabajoDuplicadoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Trabajo duplicado", response.getBody().get("job"));
    }


    @Test
    void testHandleSueldosIncorrectosException() {
        SueldosIncorrectosException exception = new SueldosIncorrectosException("Sueldos incorrectos");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleSueldosIncorrectosException(exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sueldos incorrectos", response.getBody().get("salary"));
    }

    @Test
    void testHandleNombrePaisNoEncontradoException() {
        NombrePaisNoEncontradoException exception = new NombrePaisNoEncontradoException("Nombre de país no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleNombrePaisNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nombre de país no encontrado", response.getBody().get("countryName"));
    }

    @Test
    void testHandleCityNoEncontradoException() {
        CityNoEncontradoException exception = new CityNoEncontradoException("Ciudad no encontrada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleCityNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ciudad no encontrada", response.getBody().get("city"));
    }

    @Test
    void testHandleStreetAddressNoEncontradoException() {
        StreetAddressNoEncontradoException exception = new StreetAddressNoEncontradoException("Dirección no encontrada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleStreetAddressNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dirección no encontrada", response.getBody().get("streetAddress"));
    }

    @Test
    void testHandlePostalCodeNoEncontradoException() {
        PostalCodeNoEncontradoException exception = new PostalCodeNoEncontradoException("Código postal no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handlePostalCodeNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Código postal no encontrado", response.getBody().get("postalCode"));
    }

    @Test
    void testHandleStateProvinceNoEncontradoException() {
        StateProvinceNoEncontradoException exception = new StateProvinceNoEncontradoException("Provincia no encontrada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleStateProvinceNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Provincia no encontrada", response.getBody().get("stateProvince"));
    }

    @Test
    void testHandleJobTitleNoEncontradoException() {
        JobTitleNoEncontradoException exception = new JobTitleNoEncontradoException("Título de trabajo no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleJobTitleNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Título de trabajo no encontrado", response.getBody().get("jobTitle"));
    }

    @Test
    void testHandleDepartmentNameNoEncontradoException() {
        DepartmentNameNoEncontradoException exception = new DepartmentNameNoEncontradoException("Nombre de Departamento no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleDepartmentNameNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nombre de Departamento no encontrado", response.getBody().get("departmentName"));
    }

    @Test
    void testHandleFirstNameNoEncontradoException() {
        FirstNameNoEncontradoException exception = new FirstNameNoEncontradoException("Nombre de empleado no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleFirstNameNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nombre de empleado no encontrado", response.getBody().get("firstName"));
    }

    @Test
    void testHandleLastNameNoEncontradoException() {
        LastNameNoEncontradoException exception = new LastNameNoEncontradoException("Apellido de empleado no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleLastNameNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Apellido de empleado no encontrado", response.getBody().get("lastName"));
    }

    @Test
    void testHandlePhoneNumberNoEncontradoException() {
        PhoneNumberNoEncontradoException exception = new PhoneNumberNoEncontradoException("Número de teléfono no encontrado");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handlePhoneNumberNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Número de teléfono no encontrado", response.getBody().get("phoneNumber"));
    }

    @Test
    void testHandleHireDateNoEncontradoException() {
        HireDateNoEncontradoException exception = new HireDateNoEncontradoException("Fecha no encontrada");

        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleHireDateNoEncontradoException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Fecha no encontrada", response.getBody().get("hireDate"));
    }


    @Test
    void testHandleMultipleExceptionsNotFound() {
        // Crear una MultipleException con varias excepciones diferentes
        MultipleException multipleException = new MultipleException();
        multipleException.addException(new EmpleadoNoEncontradoException("Empleado no encontrado"));
        multipleException.addException(new DepartamentoNoEncontradoException("Departamento no encontrado"));
        multipleException.addException(new ManagerNoEncontradoException("Manager no encontrado"));
        multipleException.addException(new TrabajoNoEncontradoException("Trabajo no encontrado"));
        multipleException.addException(new EmailNoEncontradoException("Email no encontrado"));
        multipleException.addException(new CityNoEncontradoException("Ciudad no encontrada"));
        multipleException.addException(new StreetAddressNoEncontradoException("Dirección no encontrada"));
        multipleException.addException(new PostalCodeNoEncontradoException("Código postal no encontrado"));
        multipleException.addException(new StateProvinceNoEncontradoException("Provincia no encontrada"));
        multipleException.addException(new JobTitleNoEncontradoException("Título de trabajo no encontrado"));
        multipleException.addException(new DepartmentNameNoEncontradoException("Nombre de Departamento no encontrado"));
        multipleException.addException(new FirstNameNoEncontradoException("Nombre de empleado no encontrado"));
        multipleException.addException(new LastNameNoEncontradoException("Apellido de empleado no encontrado"));
        multipleException.addException(new PhoneNumberNoEncontradoException("Número de teléfono no encontrado"));
        multipleException.addException(new HireDateNoEncontradoException("Fecha no encontrada"));
        multipleException.addException(new LocationNoEncontradoException("Location no encontrado"));
        multipleException.addException(new RegionNoEncontradoException("Region no encontrada"));
        multipleException.addException(new CountryNoEncontradoException("País no encontrado"));
        multipleException.addException(new SueldosIncorrectosException("Sueldos incorrectos"));
        multipleException.addException(new NombrePaisNoEncontradoException("Nombre de país no encontrado"));
        // Llamar al método handleMultipleExceptions con la MultipleException creada
        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleMultipleExceptions(multipleException);

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con el código de estado HTTP correcto
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con los mensajes de error correctos
        Map<String, String> errores = response.getBody();
        assertEquals("Empleado no encontrado", errores.get("employee"));
        assertEquals("Departamento no encontrado", errores.get("department"));
        assertEquals("Manager no encontrado", errores.get("manager"));
        assertEquals("Trabajo no encontrado", errores.get("job"));
        assertEquals("Email no encontrado", errores.get("email"));
        assertEquals("Ciudad no encontrada", errores.get("city"));
        assertEquals("Dirección no encontrada", errores.get("streetAddress"));
        assertEquals("Provincia no encontrada", errores.get("stateProvince"));
        assertEquals("Título de trabajo no encontrado", errores.get("jobTitle"));
        assertEquals("Nombre de Departamento no encontrado", errores.get("departmentName"));
        assertEquals("Nombre de empleado no encontrado", errores.get("firstName"));
        assertEquals("Apellido de empleado no encontrado", errores.get("lastName"));
        assertEquals("Número de teléfono no encontrado", errores.get("phoneNumber"));
        assertEquals("Fecha no encontrada", errores.get("hireDate"));
        assertEquals("Location no encontrado", errores.get("location"));
        assertEquals("Region no encontrada", errores.get("region"));
        assertEquals("País no encontrado", errores.get("country"));
        assertEquals("Sueldos incorrectos", errores.get("salary"));
        assertEquals("Nombre de país no encontrado", errores.get("countryName"));
    }

    @Test
    void testHandleMultipleExceptionsDuplicados() {
        // Crear una MultipleException con varias excepciones diferentes
        MultipleException multipleException = new MultipleException();
        multipleException.addException(new EmailDuplicadoException("Email duplicado"));
        multipleException.addException(new RegionDuplicadoException("Región duplicada"));
        multipleException.addException(new TrabajoDuplicadoException("Trabajo duplicado"));

        // Llamar al método handleMultipleExceptions con la MultipleException creada
        ResponseEntity<Map<String, String>> response = excepcionesHandler.handleMultipleExceptions(multipleException);

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con el código de estado HTTP correcto
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verificar que el método handleMultipleExceptions devuelve una respuesta con los mensajes de error correctos
        Map<String, String> errores = response.getBody();
        assertEquals("Email duplicado", errores.get("email"));
        assertEquals("Región duplicada", errores.get("region"));
        assertEquals("Trabajo duplicado", errores.get("job"));
    }


}
