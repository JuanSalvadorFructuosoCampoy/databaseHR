package com.hr.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

//Estas anotaciones deben ponerse en lugar de @Data. El mapeo puede dar errores si se usa la anotación @Data de Lombok.
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    private Job job;

    private float salary;

    private Float commissionPct;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonManagedReference
    private Employee manager;

    @OneToMany(mappedBy = "manager",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @OneToMany(mappedBy = "employee",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<JobHistory> jobHistories;

}
