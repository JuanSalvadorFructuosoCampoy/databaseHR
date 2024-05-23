package com.hr.EntityTest;

import com.hr.entity.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        department.setManagerId(1);
        assertEquals(1, department.getManagerId());
    }

    @Test
    void testLocationId() {
        Department department = new Department();
        department.setLocationId(1);
        assertEquals(1, department.getLocationId());
    }

    @Test
    void testAllArgsConstructor() {
        Department department = new Department(1, "HR", 1, 1);
        assertEquals(1, department.getDepartmentId());
        assertEquals("HR", department.getDepartmentName());
        assertEquals(1, department.getManagerId());
        assertEquals(1, department.getLocationId());
    }

    @Test
    void testToString() {
        Department department = new Department(1, "HR", 1, 1);
        String expectedString = "Department(departmentId=1, departmentName=HR, managerId=1, locationId=1)";
        assertEquals(expectedString, department.toString());
    }
}