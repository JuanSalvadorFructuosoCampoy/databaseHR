package com.hr.config;

import com.hr.dto.*;
import com.hr.entity.*;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.exception.LocationNoEncontradoException;
import com.hr.exception.ManagerNoEncontradoException;
import com.hr.exception.RegionNoEncontradoException;
import com.hr.repository.*;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Configuration
@NoArgsConstructor
public class ModelMapperConfig {

    private EmployeeRepository employeeRepository;
    private JobRepository jobRepository;
    private DepartmentRepository departmentRepository;
    private LocationRepository locationRepository;
    private CountryRepository countryRepository;
    private RegionRepository regionRepository;

    @Autowired
    public ModelMapperConfig(EmployeeRepository employeeRepository, JobRepository jobRepository, DepartmentRepository departmentRepository, LocationRepository locationRepository, CountryRepository countryRepository, RegionRepository regionRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    @Bean
    public ModelMapper employeeModelMapper() {
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

    public Converter<Employee, String> managerConversion = (MappingContext<Employee, String> ctx) -> {
        Employee sourceEmployee = ctx.getSource();
        if (sourceEmployee != null) {
            String firstName = sourceEmployee.getFirstName();
            String lastName = sourceEmployee.getLastName();
            return firstName + " " + lastName;
        }
        return null;
    };

    public Converter<Date, String> fechaConversion = (MappingContext<Date, String> ctx) -> {
        Date fechaContratacion = ctx.getSource();
        if (fechaContratacion != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(fechaContratacion);
        }
        return null;
    };

    public Converter<String, String> emailConversion = (MappingContext<String, String> ctx) -> {
        String email = ctx.getSource();
        if (email != null) {
            if (email.contains("@")) {
                return email;
            }
            return email.toLowerCase() + "@hr.com";
        }
        return null;
    };

    public Converter<String, Date> fechaConversionInv = (MappingContext<String, Date> ctx) -> {
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

    @Bean
    public ModelMapper jobHistoryModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<JobHistory, JobHistoryDTO> typeMap = modelMapper.createTypeMap(JobHistory.class, JobHistoryDTO.class);
        typeMap.addMappings(mapper -> mapper.using(fechaConversion).map(JobHistory::getStartDate, JobHistoryDTO::setStartDate));
        typeMap.addMappings(mapper -> mapper.using(fechaConversion).map(JobHistory::getEndDate, JobHistoryDTO::setEndDate));
        typeMap.addMappings(mapper -> mapper.using(employeeIdConversion).map(JobHistory::getEmployee, JobHistoryDTO::setEmployee));
        typeMap.addMappings(mapper -> mapper.using(jobIdConversion).map(JobHistory::getJobId, JobHistoryDTO::setJobName));
        typeMap.addMappings(mapper -> mapper.using(departmentConversion).map(JobHistory::getDepartmentId, JobHistoryDTO::setDepartment));
        return modelMapper;
    }

    public Converter<Employee, String> employeeIdConversion = (MappingContext<Employee, String> ctx) -> {
        Optional<Employee> employee = Optional.of(ctx.getSource());
        return employee.map(empl -> empl.getFirstName() + " " + empl.getLastName()).orElse(null);
    };

    public Converter<Job, String> jobIdConversion = (MappingContext<Job, String> ctx) -> {
        Optional<Job> trabajo = jobRepository.findById(ctx.getSource().getJobId());
        return trabajo.map(Job::getJobTitle).orElse(null);
    };

    public Converter<Department, String> departmentConversion = (MappingContext<Department, String> ctx) -> {
        Optional<Department> departamento = Optional.of(ctx.getSource());
        return departamento.map(Department::getDepartmentName).orElse(null);
    };

    @Bean
    public ModelMapper departmentModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Department, DepartmentDTO> typeMap = modelMapper.createTypeMap(Department.class, DepartmentDTO.class);
        typeMap.addMapping(Department::getDepartmentId, DepartmentDTO::setDepartmentId);
        typeMap.addMapping(Department::getDepartmentName, DepartmentDTO::setDepartmentName);
        typeMap.addMappings(mapper -> mapper.using(managerConversionDepart).map(Department::getManager, DepartmentDTO::setManager));
        typeMap.addMappings(mapper -> mapper.using(locationConversion).map(Department::getLocation, DepartmentDTO::setLocationId));
    return modelMapper;
    }
    public Converter<Employee, String> managerConversionDepart = (MappingContext<Employee, String> ctx) -> {
        Optional<Employee> manager = Optional.ofNullable(ctx.getSource());
        if (manager.isEmpty()) {
            return null;
        } else {
            return manager.map(employee -> employee.getFirstName() + " " + employee.getLastName()).orElseThrow(() -> new RuntimeException(new ManagerNoEncontradoException("No se ha encontrado el manager con id: " + manager.get().getEmployeeId())));
        }
    };

    public Converter<Location,Integer> locationConversion = (MappingContext<Location, Integer> ctx) -> {
        Optional<Location> location = Optional.ofNullable(ctx.getSource());
        return location.map(Location::getLocationId).orElseThrow(() -> new RuntimeException(new LocationNoEncontradoException("No se ha encontrado la localización con id: " + location.get().getLocationId())));
    };

    @Bean
    public ModelMapper locationModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Location, LocationDTO> typeMap = modelMapper.createTypeMap(Location.class, LocationDTO.class);
        typeMap.addMapping(Location::getLocationId, LocationDTO::setLocationId);
        typeMap.addMapping(Location::getStreetAddress, LocationDTO::setStreetAddress);
        typeMap.addMapping(Location::getPostalCode, LocationDTO::setPostalCode);
        typeMap.addMapping(Location::getCity, LocationDTO::setCity);
        typeMap.addMapping(Location::getStateProvince, LocationDTO::setStateProvince);
        typeMap.addMappings(mapper -> mapper.using(countryConversion).map(Location::getCountry, LocationDTO::setCountryName));

        return modelMapper;
    }

    public Converter<Country, String> countryConversion = (MappingContext<Country, String> ctx) -> {
        Optional<Country> country = Optional.of(ctx.getSource());
        return country.map(Country::getCountryName).orElseThrow(() -> new RuntimeException(new CountryNoEncontradoException("No se ha encontrado el país con id:" + country.get().getCountryId())));
    };

    @Bean
    public ModelMapper countryModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Country, CountryDTO> typeMap = modelMapper.createTypeMap(Country.class, CountryDTO.class);
        typeMap.addMapping(Country::getCountryId, CountryDTO::setCountryId);
        typeMap.addMapping(Country::getCountryName, CountryDTO::setCountryName);
        typeMap.addMappings(mapper -> mapper.using(regionConversion).map(Country::getRegion, CountryDTO::setRegion));
        return modelMapper;
    }

    public Converter<Region, String> regionConversion = (MappingContext<Region, String> ctx) -> {
        Optional<Region> region = Optional.of(ctx.getSource());
        return region.map(Region::getRegionName).orElseThrow(() -> new RuntimeException(new RegionNoEncontradoException("No se ha encontrado la región con id: " + region.get().getRegionId())));
    };

    @Bean
    public ModelMapper regionModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Region, RegionDTO> typeMap = modelMapper.createTypeMap(Region.class, RegionDTO.class);
        typeMap.addMapping(Region::getRegionId, RegionDTO::setRegionId);
        typeMap.addMapping(Region::getRegionName, RegionDTO::setRegionName);
        typeMap.addMappings(mapper -> mapper.using(countryToCountryDTOConversion).map(Region::getCountries, RegionDTO::setCountries));
        return modelMapper;
    }

    public Converter<List<Country>, List<CountryDTO>> countryToCountryDTOConversion = (MappingContext<List<Country>, List<CountryDTO>> ctx) -> {
        List<Country> countries = ctx.getSource();
        return countries.stream().map(country -> {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCountryId(country.getCountryId());
            countryDTO.setCountryName(country.getCountryName());
            countryDTO.setRegion(country.getRegion().getRegionName());
            return countryDTO;
        }).toList();
    };

    @Bean
    public ModelMapper departmentDTOGetModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Department, DepartmentDTOGet> typeMap = modelMapper.createTypeMap(Department.class, DepartmentDTOGet.class);
        typeMap.addMapping(Department::getDepartmentId, DepartmentDTOGet::setDepartmentId);
        typeMap.addMapping(Department::getDepartmentName, DepartmentDTOGet::setDepartmentName);
        typeMap.addMappings(mapper -> mapper.using(managerConversionDepart).map(Department::getManager, DepartmentDTOGet::setManager));
        typeMap.addMapping(Department::getLocation, DepartmentDTOGet::setLocation);
        return modelMapper;
    }


}