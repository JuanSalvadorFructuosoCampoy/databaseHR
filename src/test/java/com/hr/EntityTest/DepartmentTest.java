package com.hr.EntityTest;

import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Location;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentTest {

    @Test
    void testDepartmentId() {
        Department department = new Department();
        department.setDepartmentId(1);
        assertEquals(1, department.getDepartmentId());
    }

    @Test
    void testDepartmentName() {
        Department department = new Department();
        department.setDepartmentName("HR");
        assertEquals("HR", department.getDepartmentName());
    }

    @Test
    void testManagerId() {
        Department department = new Department();
        Employee manager = new Employee();
        department.setManager(manager);
        assertEquals(manager, department.getManager());
    }

    @Test
    void testLocationId() {
        Department department = new Department();
        Location location = new Location();
        department.setLocation(location);
        assertEquals(location, department.getLocation());
    }

    @Test
    void testToString() {
        Department department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("HR");
        department.setManager(new Employee());
        department.setLocation(new Location());
        String expectedString = "Department(departmentId=1, departmentName=HR, manager=Employee(employeeId=null, firstName=null, lastName=null, email=null, phoneNumber=null, hireDate=null, job=null, salary=0.0, commissionPct=null, manager=null, employees=null, department=null, jobHistories=null), location=Location(locationId=null, streetAddress=null, postalCode=null, city=null, stateProvince=null, country=null, departments=null), employees=null, jobHistories=null)";
        assertEquals(expectedString, department.toString());
    }
}
