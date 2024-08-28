package com.hr.repository;

import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT j FROM Job j WHERE j.jobTitle = :jobTitle")
    Job findByJobTitle(@Param("jobTitle") String jobTitle);

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName) = :manager")
    Employee findByManagerName(@Param("manager") String manager);

    @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName")
    Department findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT j FROM Job j WHERE j.jobTitle = :jobTitle")
    Job findByJobName(@Param("jobTitle") String jobTitle);

    Employee findByEmail(String email);
}
