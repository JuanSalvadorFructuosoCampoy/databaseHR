package com.hr.dtoTest;

import com.hr.dto.DepartmentDTOGet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentDTOGetTest {

    @Test
    void testToString() {
        DepartmentDTOGet departmentDTOGet = new DepartmentDTOGet();
        departmentDTOGet.setDepartmentId(1);
        departmentDTOGet.setDepartmentName("HR");
        departmentDTOGet.setManager("John Doe");
        departmentDTOGet.setLocation(null);
        assert departmentDTOGet.toString().equals("DepartmentDTOGet(departmentId=1, departmentName=HR, manager=John Doe, location=null)");
    }
}
