package com.hr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    private String departmentName;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "department",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Employee> employees;

    @OneToMany(mappedBy = "departmentId",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<JobHistory> jobHistories;
}
