package com.hr.repository;

import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query ("SELECT l FROM Location l WHERE l.locationId = :locationId")
    Location findByLocationId(@Param("locationId") Integer locationId);

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName) = :manager")
    Employee findByManagerName(@Param("manager") String manager);

    @Query("SELECT e FROM Employee e WHERE e.employeeId = :manager")
    Employee findByManagerId(@Param("manager") Integer manager);

}
