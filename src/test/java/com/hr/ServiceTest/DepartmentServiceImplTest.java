package com.hr.ServiceTest;

import com.hr.dto.DepartmentDTO;
import com.hr.dto.DepartmentDTOGet;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Location;
import com.hr.exception.DepartamentoNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.repository.DepartmentRepository;
import com.hr.service.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentServiceImplTest {

    @MockBean
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentServiceImpl departmentServiceImpl;

    @MockBean
    @Qualifier("departmentModelMapper")
    private ModelMapper departmentModelMapper;

    @MockBean
    @Qualifier("departmentDTOGetModelMapper")
    private ModelMapper departmentDTOGetModelMapper;

    @BeforeEach
    void setUp() {
        departmentServiceImpl = new DepartmentServiceImpl(departmentRepository, departmentModelMapper, departmentDTOGetModelMapper);
    }

    @Test
    void testConstructor() {
        assertNotNull(departmentServiceImpl);
    }

    @Test
    void testListar() {
        Department depart1 = new Department();
        depart1.setDepartmentId(1);
        depart1.setDepartmentName("IT");
        depart1.setLocation(new Location());
        depart1.setManager(new Employee());

        Department depart2 = new Department();
        depart2.setDepartmentId(1);
        depart2.setDepartmentName("IT");
        depart2.setLocation(new Location());
        depart2.setManager(new Employee());

        List<Department> departmentList = Arrays.asList(depart1, depart2);
        Page<Department> departPage = new PageImpl<>(departmentList);

        when(departmentRepository.findAll(any(Pageable.class))).thenReturn(departPage);

        DepartmentDTOGet departDTOGet1 = new DepartmentDTOGet();
        departDTOGet1.setDepartmentId(1);
        departDTOGet1.setDepartmentName("IT");

        DepartmentDTOGet departDTOGet2 = new DepartmentDTOGet();
        departDTOGet2.setDepartmentId(1);
        departDTOGet2.setDepartmentName("IT");

        when(departmentDTOGetModelMapper.map(depart1, DepartmentDTOGet.class)).thenReturn(departDTOGet1);
        when(departmentDTOGetModelMapper.map(depart2, DepartmentDTOGet.class)).thenReturn(departDTOGet2);

        Page<DepartmentDTOGet> result = departmentServiceImpl.listar(Pageable.unpaged());

        assertEquals(departPage.map(dept -> departmentDTOGetModelMapper.map(dept, DepartmentDTOGet.class)), result);
    }

    @Test
    void testPorId() throws DepartamentoNoEncontradoException {
        Department depart1 = new Department();
        depart1.setDepartmentId(1);
        depart1.setDepartmentName("IT");
        depart1.setLocation(new Location());
        depart1.setManager(new Employee());

        DepartmentDTOGet departDTO = new DepartmentDTOGet();
        departDTO.setDepartmentId(1);
        departDTO.setDepartmentName("IT");
        departDTO.setManager("Steven King");

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(depart1));
        when(departmentDTOGetModelMapper.map(any(Department.class), eq(DepartmentDTOGet.class))).thenReturn(departDTO);

        Optional<DepartmentDTOGet> result = departmentServiceImpl.porId(1);

        assertEquals(Optional.of(departDTO), result);
    }

    @Test
    void testPorIdException() {
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(DepartamentoNoEncontradoException.class, () -> departmentServiceImpl.porId(1));
    }

    @Test
    void testSave() throws MultipleException {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(1);
        departmentDTO.setDepartmentName("IT");
        departmentDTO.setLocationId(1);
        departmentDTO.setManager("Steven King");

        Department depart = new Department();
        depart.setDepartmentId(1);
        depart.setDepartmentName("IT");
        depart.setLocation(new Location());
        depart.setManager(new Employee());

        when(departmentModelMapper.map(departmentDTO, Department.class)).thenReturn(depart);
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(depart));
        when(departmentRepository.findByLocationId(anyInt())).thenReturn(new Location());
        when(departmentRepository.findByManagerName(anyString())).thenReturn(new Employee());

        DepartmentDTO result = departmentServiceImpl.save(departmentDTO);

        assertEquals(departmentModelMapper.map(depart, DepartmentDTO.class), result);
    }

    @Test
    void testSaveException() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(1);
        departmentDTO.setDepartmentName("");
        departmentDTO.setLocationId(1);
        departmentDTO.setManager("Steven King");

        Department depart = new Department();
        depart.setDepartmentId(1);
        depart.setDepartmentName("");
        depart.setLocation(new Location());
        depart.setManager(new Employee());

        when(departmentModelMapper.map(departmentDTO, Department.class)).thenReturn(depart);
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(MultipleException.class, () -> departmentServiceImpl.save(departmentDTO));
    }

    @Test
    void testEliminar() {
        Department depart = new Department();
        depart.setDepartmentId(1);
        depart.setDepartmentName("IT");
        depart.setLocation(new Location());
        depart.setManager(new Employee());

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(depart));

        assertDoesNotThrow(() -> departmentServiceImpl.eliminar(1));
    }

    @Test
    void testEliminarException() {
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(DepartamentoNoEncontradoException.class, () -> departmentServiceImpl.eliminar(1));
    }
}
