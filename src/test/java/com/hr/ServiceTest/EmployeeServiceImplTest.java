package com.hr.ServiceTest;

import com.hr.exception.*;
import com.hr.dto.EmployeeDTO;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import com.hr.repository.EmployeeRepository;
import com.hr.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeDTO employeeDTO;

    @MockBean
    @Qualifier("employeeModelMapper")
    private ModelMapper employeeModelMapper;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeModelMapper);
    }

    @Test
    void testConstructor() {
        assertNotNull(employeeService);
    }

    @Test
    void testListar() {
        // Crear una lista de Employee
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);
        Employee employee2 = new Employee();
        employee2.setEmployeeId(2);
        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        // Crear una página de Employee
        Page<Employee> employeePage = new PageImpl<>(employeeList);

        // Configurar employeeRepository para devolver la página de Employee cuando se llame a findAll
        Mockito.when(employeeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(employeePage);

        // Llamar al método listar de employeeService
        Page<EmployeeDTO> result = employeeService.listar(null, null, Pageable.unpaged());

        // Verificar que el resultado es la página de EmployeeDTO esperada
        assertEquals(employeePage.map(employee -> employeeModelMapper.map(employee, EmployeeDTO.class)), result);
    }

    @Test
    void testListarConMinSalary() {
        // Configurar los datos de prueba
        Float minSalary = 3000.00f;
        Pageable pageable = PageRequest.of(0, 5);

        // Crear una especificación simulada que represente el filtro de salario mínimo
        Specification<Employee> spec = (root, query, cb) -> cb.greaterThan(root.get("salary"), minSalary);
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2);

        // Crear una lista de empleados que cumplan con el criterio de salario mínimo
        List<Employee> employees = Arrays.asList(employee1, employee2);

        // Crear una página simulada de empleados
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        // Configurar el employeeRepository para devolver la página simulada cuando se llame a findAll con la especificación y el pageable
        when(employeeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(employeePage);

        // Configurar el employeeModelMapper para mapear cada Employee a EmployeeDTO
        Page<EmployeeDTO> result = employeeService.listar(minSalary, null, pageable);
        assertEquals(employeePage.map(employee -> employeeModelMapper.map(employee, EmployeeDTO.class)), result);
    }

    @Test
    void testPorId() throws EmpleadoNoEncontradoException {
        // Crear un Employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);

        // Crear un EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);

        // Configurar employeeRepository para devolver el Employee cuando se llame a findById
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        // Configurar employeeModelMapper para devolver el EmployeeDTO cuando se llame a map
        when(employeeModelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        // Llamar al método porId de employeeService
        Optional<EmployeeDTO> result = employeeService.porId(1);

        // Verificar que el resultado es el EmployeeDTO esperado
        assertEquals(Optional.of(employeeDTO), result);
    }

    @Test
    void testPorIdNotFound() {
        // Configurar employeeRepository para devolver un Optional vacío cuando se llame a findById
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Llamar al método porId de employeeService y verificar que se lanza una excepción
        assertThrows(EmpleadoNoEncontradoException.class, () -> employeeService.porId(1));
    }

    @Test
    void testSave() throws MultipleException, ParseException {
        // Crear un EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("EJEMPLO");
        employeeDTO.setLastName("EJEMPLO");
        employeeDTO.setPhoneNumber("123456789");
        employeeDTO.setHireDate("01/01/2020");
        employeeDTO.setEmail("ejemplo@hr.com");
        employeeDTO.setCommissionPct(0.0f);
        employeeDTO.setSalary(1500.0f);
        employeeDTO.setJobName("Nuevo");
        employeeDTO.setManager("EJEMPLO");
        employeeDTO.setDepartment("EJEMPLO");

        // Crear un Employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmail("ejemplo@hr.com");
        employee.setCommissionPct(0.0f);

        Job job = new Job();
        job.setJobId("NU");
        job.setJobTitle("Nuevo");
        job.setMinSalary(1000.0f);
        job.setMaxSalary(2000.0f);

        // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
        when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(new Job());
        when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(new Employee());
        when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(new Department());
        when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(employee);
        // Configurar employeeModelMapper para devolver el Employee cuando se llame a map
        when(employeeModelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee);
        when(employeeService.save(employeeDTO)).thenReturn(employeeDTO);

        // Llamar al método save de employeeService
        EmployeeDTO result = employeeService.save(employeeDTO);

        // Verificar que el resultado no es nulo y es el EmployeeDTO esperado
        assertNotNull(result);
        assertEquals(employeeDTO, result);
    }

    @Test
    void testSaveEmail() throws MultipleException, ParseException {
        // Crear un EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("EJEMPLO");
        employeeDTO.setLastName("EJEMPLO");
        employeeDTO.setPhoneNumber("123456789");
        employeeDTO.setHireDate("01/01/2020");
        employeeDTO.setEmail("ejemplo");
        employeeDTO.setCommissionPct(0.0f);
        employeeDTO.setSalary(1500.0f);
        employeeDTO.setJobName("Nuevo");
        employeeDTO.setManager("EJEMPLO");
        employeeDTO.setDepartment("EJEMPLO");

        // Crear un Employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmail("ejemplo");
        employee.setCommissionPct(0.0f);

        Job job = new Job();
        job.setJobId("NU");
        job.setJobTitle("Nuevo");
        job.setMinSalary(1000.0f);
        job.setMaxSalary(2000.0f);

        // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
        when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(new Job());
        when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(new Employee());
        when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(new Department());
        when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(employee);
        // Configurar employeeModelMapper para devolver el Employee cuando se llame a map
        when(employeeModelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee);
        when(employeeService.save(employeeDTO)).thenReturn(employeeDTO);

        // Llamar al método save de employeeService
        EmployeeDTO result = employeeService.save(employeeDTO);

        // Verificar que el resultado no es nulo y es el EmployeeDTO esperado
        assertNotNull(result);
        assertEquals(employeeDTO, result);
    }


@Test
void testSaveExceptions() {
    // Crear un EmployeeDTO
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setEmail("ejemplo@hr.com");
    employeeDTO.setCommissionPct(0.0f);
    employeeDTO.setSalary(1000.0f);


    // Crear un Employee
    Employee employee = new Employee();
    employee.setEmployeeId(1);
    employee.setEmail("ejemplo@hr.com");
    employee.setCommissionPct(0.0f);
    employee.setSalary(800.0f);

    Job job = new Job();
    job.setJobId("NU");
    job.setJobTitle("Nuevo");
    job.setMinSalary(1000.0f);
    job.setMaxSalary(2000.0f);

    // Configurar employeeRepository para devolver un Optional vacío cuando se llame a findById
    when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.empty());

    // Configurar employeeRepository para devolver null cuando se llamen a los métodos findBy
    when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(null);
    when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(null);
    when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(null);
    when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(null);
    when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
    // Configurar employeeModelMapper para devolver el Employee cuando se llame a map
    when(employeeModelMapper.map(employeeDTO, Employee.class)).thenReturn(employee);

    // Llamar al método save de employeeService y verificar que se lanza MultipleException
    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveExceptionsAll() throws MultipleException, ParseException {
    // Crear un EmployeeDTO
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setPhoneNumber("123456789");
    employeeDTO.setHireDate("");
    employeeDTO.setEmail("");
    employeeDTO.setCommissionPct(0.0f);
    employeeDTO.setSalary(0);
    employeeDTO.setJobName("Nuevo");
    employeeDTO.setManager("EJEMPLO");
    employeeDTO.setDepartment("EJEMPLO");

    // Crear un Employee
    Employee employee = new Employee();
    employee.setEmployeeId(1);
    employee.setEmail("john@hr.com");
    employee.setCommissionPct(0.0f);

    Job job = new Job();
    job.setJobId("NU");
    job.setJobTitle("Nuevo");
    job.setMinSalary(1000.0f);
    job.setMaxSalary(2000.0f);

    // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
    when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.of(employee));
    when(employeeRepository.save(employee)).thenReturn(employee);
    when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(job);
    when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(null);
    when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(null);
    when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
    when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(null);
    // Configurar employeeModelMapper para devolver el Employee cuando se llame a map
    when(employeeModelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee);

    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveExceptions2() {
    // Crear un EmployeeDTO
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setPhoneNumber("123456789");
    employeeDTO.setHireDate("");
    employeeDTO.setHireDate("fecha incorrecta");
    employeeDTO.setCommissionPct(0.0f);
    employeeDTO.setSalary(0);
    employeeDTO.setJobName("Nuevo");
    employeeDTO.setManager("EJEMPLO");
    employeeDTO.setDepartment("EJEMPLO");

    Job job = new Job();
    job.setJobId("NU");
    job.setJobTitle("Nuevo");
    job.setMinSalary(1000.0f);
    job.setMaxSalary(2000.0f);

    // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
    when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(job);
    when(employeeRepository.findByManagerName(anyString())).thenReturn(null);
    when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(null);
    when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveExceptions3() {
    // Crear un EmployeeDTO
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setPhoneNumber("123456789");
    employeeDTO.setHireDate("");
    employeeDTO.setHireDate("fecha incorrecta");
    employeeDTO.setCommissionPct(0.0f);
    employeeDTO.setSalary(0);
    employeeDTO.setJobName("Nuevo");
    employeeDTO.setManager("EJEMPLO");
    employeeDTO.setDepartment("EJEMPLO");

    Job job = new Job();
    job.setJobId("NU");
    job.setJobTitle("Nuevo");
    job.setMinSalary(1000.0f);
    job.setMaxSalary(2000.0f);

    // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new Employee()));
    when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(null);
    when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(null);
    when(employeeRepository.findByJobTitle(anyString())).thenReturn(job);
    when(employeeRepository.findByJobName(employeeDTO.getJobName())).thenReturn(job);
    // Configurar employeeModelMapper para devolver el Employee cuando se llame a map
    when(employeeModelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(new Employee());

    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveEmployeeThrowsException() throws ParseException {
    // Crear un EmployeeDTO con datos de prueba
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setEmail("john.doe@example.com");
    employeeDTO.setPhoneNumber("1234567890");
    employeeDTO.setHireDate("23/05/2024");
    employeeDTO.setSalary(5000);
    employeeDTO.setJobName("Developer");
    employeeDTO.setManager("Manager");
    employeeDTO.setDepartment("IT");

    // Configurar el comportamiento de los mocks para que devuelvan null o Optional.empty()
    when(employeeRepository.findById(1)).thenReturn(Optional.empty());
    when(employeeRepository.findByJobTitle("Developer")).thenReturn(null);
    when(employeeRepository.findByManagerName("Manager")).thenReturn(null);
    when(employeeRepository.findByDepartmentName("IT")).thenReturn(null);
    when(employeeRepository.findByEmail("JOHN.DOE")).thenReturn(null);

    // Llamar al método a probar y verificar que se lanza la excepción esperada
    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveEmployeeThrowsSalaryException() throws ParseException {
    // Crear un EmployeeDTO con datos de prueba
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setEmail("john.doe@example.com");
    employeeDTO.setPhoneNumber("1234567890");
    employeeDTO.setHireDate("23/05/2024");
    employeeDTO.setSalary(5000);
    employeeDTO.setJobName("Developer");
    employeeDTO.setManager("Manager");
    employeeDTO.setDepartment("IT");

    // Crear un Job con un salario mínimo mayor que el salario del EmployeeDTO
    Job job = new Job();
    job.setMinSalary(6000);
    job.setMaxSalary(7000);

    // Configurar el comportamiento de los mocks
    when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee()));
    when(employeeRepository.findByJobName("Developer")).thenReturn(job);
    when(employeeRepository.findByJobTitle("Developer")).thenReturn(job);
    when(employeeRepository.findByManagerName("Manager")).thenReturn(new Employee());
    when(employeeRepository.findByDepartmentName("IT")).thenReturn(new Department());
    when(employeeRepository.findByEmail("JOHN.DOE")).thenReturn(null);
    when(employeeModelMapper.map(employeeDTO, Employee.class)).thenReturn(new Employee());

    // Llamar al método a probar y verificar que se lanza la excepción esperada
    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testSaveEmployeeThrowsEmailException() throws ParseException {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setEmployeeId(1);
    employeeDTO.setFirstName("John");
    employeeDTO.setLastName("Doe");
    employeeDTO.setEmail("john.doe@example.com");
    employeeDTO.setPhoneNumber("1234567890");
    employeeDTO.setHireDate("23/05/2024");
    employeeDTO.setSalary(5000);
    employeeDTO.setJobName("Developer");
    employeeDTO.setManager("Manager");
    employeeDTO.setDepartment("IT");

    Employee employee = new Employee();
    employee.setEmployeeId(2);
    employee.setEmail("JOHN.DOE");

    Job job = new Job();
    job.setMinSalary(4000);
    job.setMaxSalary(7000);

    when(employeeRepository.findByEmail("JOHN.DOE")).thenReturn(employee);
    when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee()));
    when(employeeModelMapper.map(employeeDTO, Employee.class)).thenReturn(new Employee());


    assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
}

@Test
void testEliminar() throws EmpleadoNoEncontradoException {
    when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee()));

    // Llamar al método eliminar de employeeService
    employeeService.eliminar(1);

    // Verificar que el método deleteById del repositorio se llama una vez con el ID correcto
    verify(employeeRepository, Mockito.times(1)).deleteById(1);
}

@Test
void testEliminarEmpleadoNoEncontradoException() {
    // Configurar employeeRepository para devolver un Optional vacío cuando se llame a findById
    when(employeeRepository.findById(1)).thenReturn(Optional.empty());

    // Llamar al método eliminar de employeeService y verificar que se lanza EmpleadoNoEncontradoException
    assertThrows(EmpleadoNoEncontradoException.class, () -> employeeService.eliminar(1));
}
}
