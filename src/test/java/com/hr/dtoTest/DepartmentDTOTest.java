package com.hr.dtoTest;

import com.hr.dto.DepartmentDTO;
import com.hr.entity.Location;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentDTOTest {

    @Test
    void testToString() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(1);
        departmentDTO.setDepartmentName("HR");
        departmentDTO.setManager("John");
        departmentDTO.setLocationId(1);
        assert departmentDTO.toString().equals("DepartmentDTO(departmentId=1, departmentName=HR, manager=John, locationId=1)");
    }
}
