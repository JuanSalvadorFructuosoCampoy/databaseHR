package com.hr.dtoTest;

import com.hr.dto.EmployeeDTO;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeDTOTest {

    @Test
    void testAllArgsConstructor() {

        EmployeeDTO employee = new EmployeeDTO(1, "HR","HR", "HR", "222.222.222.222","09/10/1990","Programmer", 2000f, null, "Steven King", "IT");

        assertEquals(1, employee.getEmployeeId());
        assertEquals("HR", employee.getFirstName());
        assertEquals("HR", employee.getLastName());
        assertEquals("HR", employee.getEmail());
        assertEquals("222.222.222.222", employee.getPhoneNumber());
        assertEquals("09/10/1990", employee.getHireDate());
        assertEquals("Programmer", employee.getJobName());
        assertEquals(2000f, employee.getSalary());
        assertEquals(null, employee.getCommissionPct());
        assertEquals("Steven King", employee.getManager());
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void testToString() {
        Employee manager = new Employee();
        Department department = new Department();
        Job job = new Job();
        EmployeeDTO employee = new EmployeeDTO(1, "HR","HR", "HR", "222.222.222.222","09/10/1990","Programmer", 2000f, null, "Steven King", "IT");
        String expectedString = "EmployeeDTO(employeeId=1, firstName=HR, lastName=HR, email=HR, phoneNumber=222.222.222.222, hireDate=09/10/1990, jobName=Programmer, salary=2000.0, commissionPct=null, manager=Steven King, department=IT)";
        assertEquals(expectedString, employee.toString());
    }
}
