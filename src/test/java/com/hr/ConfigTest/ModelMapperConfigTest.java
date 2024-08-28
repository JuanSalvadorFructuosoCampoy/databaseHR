package com.hr.ConfigTest;

import com.hr.config.ModelMapperConfig;
import com.hr.dto.CountryDTO;
import com.hr.entity.*;
import com.hr.repository.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ModelMapperConfigTest {
    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    JobRepository jobRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @MockBean
    LocationRepository locationRepository;

    @MockBean
    CountryRepository countryRepository;

    @MockBean
    RegionRepository regionRepository;

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

    @Test
    void testEmployeeIdConversion() {
        // Crear una instancia de ModelMapperConfig
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);

        // Configurar el mock para devolver un empleado específico cuando se llame al método findById
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        // Obtener el Converter y realizar la prueba
        Converter<Employee,String> employeeIdConversion = modelMapperConfig.employeeIdConversion;
        MappingContext<Employee,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(employee);

        String result = employeeIdConversion.convert(context);
        assertEquals("John Doe", result);
    }

    @Test
    void testJobIdConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Job job = new Job();
        job.setJobId("JOB_ID"); // Asegúrate de que el jobId está establecido
        when(jobRepository.findById(job.getJobId())).thenReturn(Optional.of(job));

        // Obtener el Converter y realizar la prueba
        Converter<Job,String> jobIdConversion = modelMapperConfig.jobIdConversion;
        MappingContext<Job,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(job);

        String result = jobIdConversion.convert(context);
        assertEquals(job.getJobTitle(), result);
    }

    @Test
    void testDepartmentConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Department department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("Department Name");
        when(departmentRepository.findById(department.getDepartmentId())).thenReturn(Optional.of(department));

        // Obtener el Converter y realizar la prueba
        Converter<Department,String> departmentConversion = modelMapperConfig.departmentConversion;
        MappingContext<Department,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(department);

        String result = departmentConversion.convert(context);
        assertEquals(department.getDepartmentName(), result);
    }

    @Test
    void testLocationConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Location location = new Location();
        location.setLocationId(1);
        when(locationRepository.findById(location.getLocationId())).thenReturn(Optional.of(location));

        Converter<Location, Integer> locationConversion = modelMapperConfig.locationConversion;
        MappingContext<Location, Integer> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(location);

        Integer result = locationConversion.convert(context);
        assertEquals(1, result);
    }

    @Test
    void testManagerConversionDepart() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        Converter<Employee,String> managerConversionDepart = modelMapperConfig.managerConversionDepart;
        MappingContext<Employee,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(employee);

        String result = managerConversionDepart.convert(context);
        assertEquals(employee.getFirstName() + " " + employee.getLastName(), result);
    }

    @Test
    void testCountryConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Country country = new Country();
        country.setCountryId("ES");
        country.setCountryName("Spain");
        when(countryRepository.findById(country.getCountryId())).thenReturn(Optional.of(country));

        Converter<Country,String> countryConversion = modelMapperConfig.countryConversion;
        MappingContext<Country,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(country);

        String result = countryConversion.convert(context);
        assertEquals(country.getCountryName(), result);
    }

    @Test
    void testRegionConversion() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);
        Region region = new Region();
        region.setRegionId(1);
        region.setRegionName("Region Name");
        when(regionRepository.findById(region.getRegionId())).thenReturn(Optional.of(region));

        Converter<Region,String> regionConversion = modelMapperConfig.regionConversion;
        MappingContext<Region,String> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(region);

        String result = regionConversion.convert(context);
        assertEquals(region.getRegionName(), result);
    }

    @Test
    void testCountryToCountryDTOConversion() {
        // Crear un ModelMapperConfig
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig(employeeRepository, jobRepository, departmentRepository, locationRepository, countryRepository, regionRepository);

        // Crear una lista de Country
        List<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.setCountryId("ES");
        country.setCountryName("Spain");
        Region region = new Region();
        region.setRegionName("Europe");
        country.setRegion(region);
        countries.add(country);

        // Mockear el MappingContext
        MappingContext<List<Country>, List<CountryDTO>> context = mock(MappingContext.class);
        when(context.getSource()).thenReturn(countries);

        // Llamar al método countryToCountryDTOConversion.convert()
        List<CountryDTO> countryDTOs = modelMapperConfig.countryToCountryDTOConversion.convert(context);

        // Verificar que la lista de CountryDTO tiene los valores correctos
        assertEquals(1, countryDTOs.size());
        CountryDTO countryDTO = countryDTOs.get(0);
        assertEquals(country.getCountryId(), countryDTO.getCountryId());
        assertEquals(country.getCountryName(), countryDTO.getCountryName());
        assertEquals(country.getRegion().getRegionName(), countryDTO.getRegion());
    }
}
