package com.hr.exceptionhandler;

import com.hr.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExcepcionesHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("employee", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DepartamentoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleDepartamentoNoEncontradoException(DepartamentoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("department", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ManagerNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleManagerNoEncontradoException(ManagerNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("manager", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TrabajoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleTrabajoNoEncontradoException(TrabajoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("job", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleEmailDuplicadoException(EmailDuplicadoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("email", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RegionDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleRegionDuplicadoException(RegionDuplicadoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("region", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TrabajoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleTrabajoDuplicadoException(TrabajoDuplicadoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("job", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LocationNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleLocationNoEncontradoException(LocationNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("location", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SueldosIncorrectosException.class)
    public ResponseEntity<Map<String, String>> handleSueldosIncorrectosException(SueldosIncorrectosException e) {
        Map<String, String> error = new HashMap<>();
        error.put("salary", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RegionNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleRegionNoEncontradoException(RegionNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("region", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleEmailNoEncontradoException(EmailNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("email", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CountryNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleCountryNoEncontradoException(CountryNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("pais", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NombrePaisNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNombrePaisNoEncontradoException(NombrePaisNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("countryName", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StreetAddressNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleStreetAddressNoEncontradoException(StreetAddressNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("streetAddress", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PostalCodeNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePostalCodeNoEncontradoException(PostalCodeNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("postalCode", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(CityNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleCityNoEncontradoException(CityNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("city", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StateProvinceNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleStateProvinceNoEncontradoException(StateProvinceNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("stateProvince", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(JobTitleNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleJobTitleNoEncontradoException(JobTitleNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("jobTitle", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DepartmentNameNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleDepartmentNameNoEncontradoException(DepartmentNameNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("departmentName", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FirstNameNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleFirstNameNoEncontradoException(FirstNameNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("firstName", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LastNameNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleLastNameNoEncontradoException(LastNameNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("lastName", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PhoneNumberNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePhoneNumberNoEncontradoException(PhoneNumberNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("phoneNumber", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HireDateNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleHireDateNoEncontradoException(HireDateNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("hireDate", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MultipleException.class)
    public ResponseEntity<Map<String, String>> handleMultipleExceptions(MultipleException e) {
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
            } else if (exception instanceof LocationNoEncontradoException) {
                errores.put("location", exception.getMessage());
            } else if (exception instanceof RegionNoEncontradoException) {
                errores.put("region", exception.getMessage());
            } else if (exception instanceof CountryNoEncontradoException) {
                errores.put("country", exception.getMessage());
            } else if (exception instanceof TrabajoDuplicadoException) {
                errores.put("job", exception.getMessage());
            } else if (exception instanceof RegionDuplicadoException) {
                errores.put("region", exception.getMessage());
            } else if (exception instanceof SueldosIncorrectosException) {
                errores.put("salary", exception.getMessage());
            } else if (exception instanceof EmailNoEncontradoException) {
                errores.put("email", exception.getMessage());
            } else if (exception instanceof NombrePaisNoEncontradoException) {
                errores.put("countryName", exception.getMessage());
            } else if (exception instanceof StreetAddressNoEncontradoException) {
                errores.put("streetAddress", exception.getMessage());
            } else if (exception instanceof PostalCodeNoEncontradoException) {
                errores.put("postalCode", exception.getMessage());
            } else if (exception instanceof CityNoEncontradoException) {
                errores.put("city", exception.getMessage());
            } else if (exception instanceof StateProvinceNoEncontradoException) {
                errores.put("stateProvince", exception.getMessage());
            } else if (exception instanceof DepartmentNameNoEncontradoException) {
                errores.put("departmentName", exception.getMessage());
            } else if (exception instanceof JobTitleNoEncontradoException) {
                errores.put("jobTitle", exception.getMessage());
            } else if (exception instanceof FirstNameNoEncontradoException) {
                errores.put("firstName", exception.getMessage());
            } else if (exception instanceof LastNameNoEncontradoException) {
                errores.put("lastName", exception.getMessage());
            } else if (exception instanceof PhoneNumberNoEncontradoException) {
                errores.put("phoneNumber", exception.getMessage());
            } else if (exception instanceof HireDateNoEncontradoException) {
                errores.put("hireDate", exception.getMessage());
            }
        }
        return ResponseEntity.badRequest().body(errores);
    }
}
