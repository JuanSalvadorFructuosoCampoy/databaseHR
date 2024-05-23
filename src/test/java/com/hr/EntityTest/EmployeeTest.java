package com.hr.EntityTest;

import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {

    @Test
    void testEmployeeId() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        assertEquals(1, employee.getEmployeeId());
    }

    @Test
    void testEmployeeFirstName() {
        Employee employee = new Employee();
        employee.setFirstName("HR");
        assertEquals("HR", employee.getFirstName());
    }

    @Test
    void testEmployeeLastName() {
        Employee employee = new Employee();
        employee.setLastName("HR");
        assertEquals("HR", employee.getLastName());
    }

    @Test
    void testEmployeeEmail() {
        Employee employee = new Employee();
        employee.setEmail("HR");
        assertEquals("HR", employee.getEmail());
    }

    @Test
    void testEmployeePhoneNumber() {
        Employee employee = new Employee();
        employee.setPhoneNumber("222.222.222.222");
        assertEquals("222.222.222.222", employee.getPhoneNumber());
    }

    @Test
    void testEmployeeHireDate() {
        Employee employee = new Employee();
        employee.setHireDate(new Date("2021/10/10"));
        assertEquals(new Date("2021/10/10"), employee.getHireDate());
    }

    @Test
    void testEmployeeJob() {
        Employee employee = new Employee();
        Job job = new Job();
        employee.setJob(job);
        assertEquals(job, employee.getJob());
    }

    @Test
    void testEmployeeManager() {
        Employee employee = new Employee();
        Employee manager = new Employee();
        employee.setManager(manager);
        assertEquals(manager, employee.getManager());
    }

    @Test
    void testEmployeeDepartment() {
        Employee employee = new Employee();
        Department department = new Department(1, "HR", 1, 1);
        employee.setDepartment(department);
        Department employeeDepartment = employee.getDepartment();

        assertEquals(department.getDepartmentId(), employeeDepartment.getDepartmentId());
        assertEquals(department.getDepartmentName(), employeeDepartment.getDepartmentName());
        assertEquals(department.getManagerId(), employeeDepartment.getManagerId());
        assertEquals(department.getLocationId(), employeeDepartment.getLocationId());
    }


    @Test
    void testEmployeeSalary() {
        Employee employee = new Employee();
        employee.setSalary(1000F);
        assertEquals(1000F, employee.getSalary());
    }

    @Test
    void testEmployeeCommissionPct() {
        Employee employee = new Employee();
        employee.setCommissionPct(null);
        assertEquals(null, employee.getCommissionPct());
    }

    @Test
    void testAllArgsConstructor() {
        Employee manager = new Employee();
        Job job = new Job();
        Department department = new Department();
        Employee employee = new Employee(1, "HR","HR", "HR", "222.222.222.222",new Date("2021/10/10"),job, 2000f, null, manager, department);

        assertEquals(1, employee.getEmployeeId());
        assertEquals("HR", employee.getFirstName());
        assertEquals("HR", employee.getLastName());
        assertEquals("HR", employee.getEmail());
        assertEquals("222.222.222.222", employee.getPhoneNumber());
        assertEquals(new Date("2021/10/10"), employee.getHireDate());
        assertEquals(job, employee.getJob());
        assertEquals(2000f, employee.getSalary());
        assertEquals(null, employee.getCommissionPct());
        assertEquals(manager, employee.getManager());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void testToString() {
        Employee manager = new Employee();
        Department department = new Department();
        Job job = new Job();
        Employee employee = new Employee(1, "HR","HR", "HR", "222.222.222.222",new Date("2021/10/10"), job, 2000f, null, manager, department);
        String expectedString = "Employee(employeeId=1, firstName=HR, lastName=HR, email=HR, phoneNumber=222.222.222.222, hireDate=Sun Oct 10 00:00:00 CEST 2021, job=Job(jobId=null, jobTitle=null, minSalary=0.0, maxSalary=0.0), salary=2000.0, commissionPct=null, manager=Employee(employeeId=null, firstName=null, lastName=null, email=null, phoneNumber=null, hireDate=null, job=null, salary=0.0, commissionPct=null, manager=null, department=null), department=Department(departmentId=null, departmentName=null, managerId=null, locationId=null))";
        assertEquals(expectedString, employee.toString());
    }
}
