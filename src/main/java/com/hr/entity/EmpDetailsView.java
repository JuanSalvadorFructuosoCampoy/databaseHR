package com.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "emp_details_view")
public class EmpDetailsView {
    @Id
    private Integer employeeId;
    private String jobId;
    private Integer managerId;
    private Integer departmentId;
    private Integer locationId;
    private char countryId;
    private String firstName;
    private String lastName;
    private Float salary;
    private Float commissionPct;
    private String departmentName;
    private String jobTitle;
    private String city;
    private String stateProvince;
    private String countryName;
    private String regionName;
}
