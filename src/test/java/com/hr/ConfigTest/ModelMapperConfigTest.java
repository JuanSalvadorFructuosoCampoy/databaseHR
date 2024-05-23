package com.hr.ConfigTest;

import com.hr.config.ModelMapperConfig;
import com.hr.entity.Employee;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ModelMapperConfigTest {

    @Test
    void testManagerConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<Employee, String> managerConversion = modelMapperConfig.managerConversion;

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        MappingContext<Employee, String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(employee);

        String result = managerConversion.convert(context);

        assertEquals("John Doe", result);
    }

    @Test
    void testManagerConversionNull() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<Employee, String> managerConversion = modelMapperConfig.managerConversion;

        MappingContext context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(null);

        String result = managerConversion.convert(context);

        assertNull(result);
    }

    @Test
    void testFechaConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<Date, String> fechaConversion = modelMapperConfig.fechaConversion;

        Employee employee = new Employee();
        employee.setHireDate(new Date("2021/01/01"));

        MappingContext<Date, String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(employee.getHireDate());

        String result = fechaConversion.convert(context);

        assertEquals("01/01/2021", result);
    }

    @Test
    void testFechaConversionNull() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<Date, String> fechaConversion = modelMapperConfig.fechaConversion;

        MappingContext context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(null);

        String result = fechaConversion.convert(context);

        assertNull(result);
    }

    @Test
    void testEmailConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, String> emailConversion = modelMapperConfig.emailConversion;

        Employee employee = new Employee();
        employee.setEmail("test@hr.com");

        MappingContext<String, String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn("test@hr.com");

        String result = emailConversion.convert(context);
        assertEquals(employee.getEmail(), result);
    }

    @Test
    void testEmailConversionSinArroba() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, String> emailConversion = modelMapperConfig.emailConversion;

        Employee employee = new Employee();
        employee.setEmail("test@hr.com");

        MappingContext<String, String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn("test");

        String result = emailConversion.convert(context);
        assertEquals(employee.getEmail(), result);
    }

    @Test
    void testEmailConversionNull() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, String> emailConversion = modelMapperConfig.emailConversion;

        MappingContext<String, String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(null);

        String result = emailConversion.convert(context);
        assertNull(result);
    }

    @Test
    void testFechaConversionInv() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, Date> fechaConversionInv = modelMapperConfig.fechaConversionInv;

        Employee employee = new Employee();
        employee.setHireDate(new Date("2021/01/01"));

        MappingContext<String, Date> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn("01/01/2021");

        Date result = fechaConversionInv.convert(context);

        assertEquals(employee.getHireDate(), result);
    }

    @Test
    void testFechaConversionInvNull() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, Date> fechaConversionInv = modelMapperConfig.fechaConversionInv;

        MappingContext<String, Date> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(null);

        Date result = fechaConversionInv.convert(context);

        assertNull(result);
    }

    @Test
    void testFechaConversionInvParseException() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        Converter<String, Date> fechaConversionInv = modelMapperConfig.fechaConversionInv;

        MappingContext<String, Date> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn("fecha mal formateada");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fechaConversionInv.convert(context);
        });

        String expectedMessage = "Invalid date format. Expected dd/MM/yyyy, got: fecha mal formateada";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
