package com.hr.EntityTest;

import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import com.hr.entity.JobHistory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryTest {

    @Test
    void toStringTest() throws ParseException {
        JobHistory jobHistory = new JobHistory();
        Employee employee = new Employee();
        Job job = new Job();
        Department department = new Department();
        jobHistory.setEmployee(employee);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fixedDate = sdf.parse("21/05/2024 10:00:00");
        jobHistory.setStartDate(fixedDate);
        jobHistory.setJobId(job);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fixedDate2 = sdf2.parse("21/05/2024 10:00:00");
        jobHistory.setEndDate(fixedDate2);
        jobHistory.setDepartmentId(department);
        assertEquals(jobHistory.toString(),"JobHistory(employee=Employee(employeeId=null, firstName=null, lastName=null, email=null, phoneNumber=null, hireDate=null, job=null, salary=0.0, commissionPct=null, manager=null, employees=null, department=null, jobHistories=null), startDate=Tue May 21 10:00:00 CEST 2024, jobId=Job(jobId=null, jobTitle=null, minSalary=0.0, maxSalary=0.0, employee=null, jobHistories=null), endDate=Tue May 21 10:00:00 CEST 2024, departmentId=Department(departmentId=null, departmentName=null, manager=null, location=null, employees=null, jobHistories=null))");
    }
}
