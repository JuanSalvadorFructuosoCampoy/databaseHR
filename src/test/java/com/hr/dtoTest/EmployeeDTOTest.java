package com.hr.dtoTest;

import com.hr.dto.EmployeeDTO;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeDTOTest {

    @Test
    void testToString() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmployeeId(1);
        employee.setFirstName("HR");
        employee.setLastName("HR");
        employee.setEmail("HR");
        employee.setPhoneNumber("222.222.222.222");
        employee.setHireDate("09/10/1990");
        employee.setJobName("Programmer");
        employee.setSalary(2000.0f);
        employee.setManager("manager");
        String expectedString = "EmployeeDTO(employeeId=1, firstName=HR, lastName=HR, email=HR, phoneNumber=222.222.222.222, hireDate=09/10/1990, jobName=Programmer, salary=2000.0, commissionPct=null, manager=manager, department=null)";
        assertEquals(expectedString, employee.toString());
    }
}
