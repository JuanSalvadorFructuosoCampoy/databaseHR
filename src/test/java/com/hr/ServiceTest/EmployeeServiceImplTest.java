package com.hr.ServiceTest;

import com.hr.TestConfiguration.ModelMapperTestConfig;
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
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeDTO employeeDTO;

    @MockBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository, modelMapper);
        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(100);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@hr.com");
        employeeDTO.setPhoneNumber("123456789");
        employeeDTO.setHireDate("2021-01-01");
        employeeDTO.setJobName("Developer");
        employeeDTO.setSalary(1000);
        employeeDTO.setCommissionPct(null);
        employeeDTO.setManager("Steven King");
        employeeDTO.setDepartment("IT");

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
        Page<EmployeeDTO> result = employeeService.listar(null, Pageable.unpaged());

        // Verificar que el resultado es la página de EmployeeDTO esperada
        assertEquals(employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class)), result);
    }

    @Test
    void testListarConMinSalary() {
        // Configurar los datos de prueba
        Float minSalary = 3000.00f;
        Pageable pageable = PageRequest.of(0, 5);

        // Crear una especificación simulada que represente el filtro de salario mínimo
        Specification<Employee> spec = (root, query, cb) -> cb.greaterThan(root.get("salary"), minSalary);

        // Crear una lista de empleados que cumplan con el criterio de salario mínimo
        List<Employee> employees = Arrays.asList(
                new Employee(1, "John", "Doe","JOHNDOE", "123456789", new Date("2002/10/09"), new Job(), 4000.00f, null, new Employee(), new Department()),
                new Employee(2, "Mary", "Sue","MARYSUE", "123456789", new Date("2002/10/09"), new Job(), 4000.00f, null, new Employee(), new Department())
        );

        // Crear una página simulada de empleados
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        // Configurar el employeeRepository para devolver la página simulada cuando se llame a findAll con la especificación y el pageable
        when(employeeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(employeePage);        // Configurar el modelMapper para mapear cada Employee a EmployeeDTO
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class)))
                .thenAnswer(invocation -> {
                    Employee employee = invocation.getArgument(0);
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setEmployeeId(employee.getEmployeeId());
                    employeeDTO.setSalary(employee.getSalary());
                    return employeeDTO;
                });
        // Llamar al método listar y obtener el resultado
        Page<EmployeeDTO> result = employeeService.listar(minSalary, pageable);

        // Verificar que el resultado contiene la cantidad correcta de empleados
        assertEquals(2, result.getContent().size(), "La cantidad de empleados devueltos no es la esperada");

        // Verificar que todos los empleados en el resultado tienen un salario mayor que el salario mínimo
        assertTrue(result.getContent().stream().allMatch(e -> e.getSalary() > minSalary));
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

        // Configurar modelMapper para devolver el EmployeeDTO cuando se llame a map
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

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
    void testSave() throws MultipleException {
        // Crear un EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setEmail("ejemplo@hr.com");
        // Crear un Employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmail("ejemplo@hr.com");

        // Configurar employeeRepository para devolver el Employee cuando se llame a findById y save
        when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(new Job());
        when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(new Employee());
        when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(new Department());
        // Configurar modelMapper para devolver el Employee cuando se llame a map
        when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee);
        when(employeeService.save(employeeDTO)).thenReturn(employeeDTO);

        // Llamar al método save de employeeService
        EmployeeDTO result = employeeService.save(employeeDTO);

        // Verificar que el resultado no es nulo y es el EmployeeDTO esperado
        assertNotNull(result);
        assertEquals(employeeDTO, result);
    }

    @Test
    void testSaveExceptions() throws MultipleException {
        // Crear un EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setEmail("ejemplo@hr.com");

        // Crear un Employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmail("ejemplo@hr.com");

        // Configurar employeeRepository para devolver un Optional vacío cuando se llame a findById
        when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.empty());

        // Configurar employeeRepository para devolver null cuando se llamen a los métodos findBy
        when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(null);
        when(employeeRepository.findByManagerName(employeeDTO.getManager())).thenReturn(null);
        when(employeeRepository.findByDepartmentName(employeeDTO.getDepartment())).thenReturn(null);
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(null);

        // Configurar modelMapper para devolver el Employee cuando se llame a map
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(employee);

        // Llamar al método save de employeeService y verificar que se lanza MultipleException
        assertThrows(MultipleException.class, () -> employeeService.save(employeeDTO));
    }

    @Test
    void testSaveEmailDuplicadoException() {
        // Crear un EmployeeDTO con un email que se espera esté duplicado
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setEmail("ejemplo@hr.com");

        // Crear un Employee simulado con el mismo email para simular la duplicación
        Employee employeeSimulado = new Employee();
        employeeSimulado.setEmployeeId(2); // ID diferente para simular un empleado diferente
        employeeSimulado.setEmail("ejemplo@hr.com");

        // Configurar el comportamiento del repository para simular la situación de email duplicado
        when(employeeRepository.findByEmail("EJEMPLO")).thenReturn(employeeSimulado);

        // Configurar el modelMapper para que devuelva un Employee cuando se mapee desde EmployeeDTO
        when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(new Employee());

        // Ejecutar el método save y esperar que se lance la MultipleException
        MultipleException multipleException = assertThrows(MultipleException.class, () -> {
            employeeService.save(employeeDTO);
        });

        // Verificar que la MultipleException contiene una EmailDuplicadoException
        assertTrue(multipleException.getExceptions().stream()
                        .anyMatch(e -> e instanceof EmailDuplicadoException));
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
