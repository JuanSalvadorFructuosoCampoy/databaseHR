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
@Table(name = "jobs")
public class Job {

    @Id
    private String jobId;

    private String jobTitle;

    private float minSalary;

    private float maxSalary;

    @OneToMany(mappedBy = "job",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employee;

    @OneToMany(mappedBy = "jobId",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JobHistory> jobHistories;
}
