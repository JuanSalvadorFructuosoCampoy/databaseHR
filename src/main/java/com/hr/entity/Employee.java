package com.hr.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

//Estas anotaciones deben ponerse en lugar de @Data. El mapeo puede dar errores si se usa la anotación @Data de Lombok.
@Getter
@Setter
@ToString
@AllArgsConstructor
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
    private Job job;

    private float salary;

    private Float commissionPct;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonManagedReference
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonManagedReference
    private Department department;

}
