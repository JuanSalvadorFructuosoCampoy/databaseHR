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
        Department department = new Department();
        employee.setDepartment(department);
        Department employeeDepartment = employee.getDepartment();

        assertEquals(department.getDepartmentId(), employeeDepartment.getDepartmentId());
        assertEquals(department.getDepartmentName(), employeeDepartment.getDepartmentName());
        assertEquals(department.getManager(), employeeDepartment.getManager());
        assertEquals(department.getLocation(), employeeDepartment.getLocation());
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
    void testToString() {
        Employee manager = new Employee();
        Department department = new Department();
        Job job = new Job();
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("HR");
        employee.setLastName("HR");
        employee.setEmail("HR");
        employee.setPhoneNumber("222.222.222.222");
        employee.setHireDate(new Date("2021/10/10"));
        employee.setJob(job);
        employee.setSalary(2000f);
        employee.setCommissionPct(null);
        employee.setManager(manager);
        employee.setDepartment(department);

        String expectedString = "Employee(employeeId=1, firstName=HR, lastName=HR, email=HR, phoneNumber=222.222.222.222, hireDate=Sun Oct 10 00:00:00 CEST 2021, job=Job(jobId=null, jobTitle=null, minSalary=0.0, maxSalary=0.0, employee=null, jobHistories=null), salary=2000.0, commissionPct=null, manager=Employee(employeeId=null, firstName=null, lastName=null, email=null, phoneNumber=null, hireDate=null, job=null, salary=0.0, commissionPct=null, manager=null, employees=null, department=null, jobHistories=null), employees=null, department=Department(departmentId=null, departmentName=null, manager=null, location=null, employees=null, jobHistories=null), jobHistories=null)";
        assertEquals(expectedString, employee.toString());
    }
}
