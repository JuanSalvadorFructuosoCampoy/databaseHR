package com.hr.TestConfiguration;

import com.hr.dto.EmployeeDTO;
import com.hr.entity.Employee;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestConfiguration
public class ModelMapperTestConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Employee, EmployeeDTO> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.addMapping(mapper -> mapper.getDepartment().getDepartmentName(), EmployeeDTO::setDepartment);
        typeMap.addMapping(Employee::getFirstName, EmployeeDTO::setFirstName);
        typeMap.addMapping(Employee::getLastName, EmployeeDTO::setLastName);
        typeMap.addMappings(mapper -> mapper.using(emailConversion).map(Employee::getEmail, EmployeeDTO::setEmail));
        typeMap.addMapping(Employee::getPhoneNumber, EmployeeDTO::setPhoneNumber);
        typeMap.addMappings(mapper -> mapper.using(fechaConversion).map(Employee::getHireDate, EmployeeDTO::setHireDate));
        typeMap.addMapping(mapper -> mapper.getJob().getJobTitle(), EmployeeDTO::setJobName);
        typeMap.addMapping(Employee::getSalary, EmployeeDTO::setSalary);
        typeMap.addMapping(Employee::getCommissionPct, EmployeeDTO::setCommissionPct);
        typeMap.addMappings(mapper -> mapper.using(managerConversion).map(Employee::getManager, EmployeeDTO::setManager));

        TypeMap<EmployeeDTO, Employee> typeMapInv = modelMapper.createTypeMap(EmployeeDTO.class, Employee.class);
        typeMapInv.addMappings(mapper -> mapper.using(fechaConversionInv).map(EmployeeDTO::getHireDate, Employee::setHireDate));

        return modelMapper;
    }

    //Esta es una conversión de Employee a String usando una clase anónima que implementa la interfaz Converter.
    //Las clases anónimas sirven para implementar interfaces o clases abstractas sin necesidad de crear una clase concreta, es decir,
    //cuando se necesita una implementación única de una interfaz o clase abstracta.

    //Esta primera versión hace uso de una clase anónima que implementa la interfaz Converter, sin expresión lambda.
//    Converter<Employee,String> managerConversion = new Converter<Employee, String>() {
//        public String convert(MappingContext<Employee, String> ctx) {
//            Employee sourceEmployee = ctx.getSource();
//            if (sourceEmployee != null && sourceEmployee.getManager() != null) {
//                String firstName = sourceEmployee.getManager().getFirstName();
//                String lastName = sourceEmployee.getManager().getLastName();
//                return firstName + " " + lastName;
//            }
//            return null;
//        }
//    };


    //Este es el código con la expresión lambda:
    //La expresión lambda se puede hacer porque la interfaz Converter es una interfaz funcional, es decir, una interfaz que tiene un solo método abstracto.
    //Por tanto, se define una expresión lambda que implementa el método convert de la interfaz Converter en un mismo paso.

    //Usamos sourceEmployee.getFirstName y getLastName en lugar de sourceEmployee.getManager().getFirstName y getLastName porque
    //en el addMappings ya estamos indicando que es el manager el que queremos mapear.
    Converter<Employee, String> managerConversion = (MappingContext<Employee, String> ctx) -> {
        Employee sourceEmployee = ctx.getSource();
        if (sourceEmployee != null) {
            String firstName = sourceEmployee.getFirstName();
            String lastName = sourceEmployee.getLastName();
            return firstName + " " + lastName;
        }
        return null;
    };

    Converter<Date, String> fechaConversion = (MappingContext<Date, String> ctx) -> {
        Date fechaContratacion = ctx.getSource();
        if (fechaContratacion != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(fechaContratacion);
        }
        return null;
    };

    Converter<String, String> emailConversion = (MappingContext<String, String> ctx) -> {
        String email = ctx.getSource();
        if (email != null) {
            if(email.contains("@")) {
                return email;
            }
            return email.toLowerCase() + "@hr.com";
        }
        return null;
    };

    Converter<String, Date> fechaConversionInv = (MappingContext<String, Date> ctx) -> {
        String fechaContratacion = ctx.getSource();
        if (fechaContratacion != null) {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = inputFormatter.parse(fechaContratacion);
                String formattedDate = outputFormatter.format(date);
                return outputFormatter.parse(formattedDate);
            } catch (ParseException e) {
                throw new RuntimeException("Invalid date format. Expected dd/MM/yyyy, got: " + fechaContratacion);
            }
        }
        return null;
    };
}
