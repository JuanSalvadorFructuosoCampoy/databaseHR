package com.hr.ServiceTest;

import com.hr.entity.EmpDetailsView;
import com.hr.exception.EmpleadoNoEncontradoException;
import com.hr.repository.EmpDetailsViewRepository;
import com.hr.service.EmpDetailsViewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EmpDetailsViewServiceImplTest {

    @MockBean
    private EmpDetailsViewRepository empDetailsViewRepository;

    @Mock
    private EmpDetailsViewServiceImpl empDetailsViewServiceImpl;

    @BeforeEach
    void setUp() {
        empDetailsViewServiceImpl = new EmpDetailsViewServiceImpl(empDetailsViewRepository);
    }

    @Test
    void testListar() {
        EmpDetailsView empDetailsView = new EmpDetailsView();
        empDetailsView.setEmployeeId(1);

        EmpDetailsView empDetailsView2 = new EmpDetailsView();
        empDetailsView2.setEmployeeId(2);

        List<EmpDetailsView> detailsList = List.of(empDetailsView, empDetailsView2);
        Page<EmpDetailsView> detailsPage = new PageImpl<>(detailsList);

        when(empDetailsViewRepository.findAll(any(Pageable.class))).thenReturn(detailsPage);

        Page<EmpDetailsView> result = empDetailsViewServiceImpl.listar(Pageable.unpaged());

        assertEquals(detailsPage, result);
    }

    @Test
    void testPorId() throws EmpleadoNoEncontradoException {
        EmpDetailsView empDetailsView = new EmpDetailsView();
        empDetailsView.setEmployeeId(1);

        when(empDetailsViewRepository.findById(anyInt())).thenReturn(Optional.of(empDetailsView));

        EmpDetailsView result = empDetailsViewServiceImpl.porId(1).get();

        assertEquals(empDetailsView, result);
    }

    @Test
    void testPorIdException() {
        when(empDetailsViewRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EmpleadoNoEncontradoException.class, () -> empDetailsViewServiceImpl.porId(1));
    }
}
