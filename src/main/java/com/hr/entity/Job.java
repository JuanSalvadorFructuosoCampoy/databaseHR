package com.hr.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    private String jobId;

    private String jobTitle;

    private float minSalary;

    private float maxSalary;
}
