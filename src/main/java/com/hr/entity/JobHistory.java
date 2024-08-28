package com.hr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(JobHistoryId.class)
public class JobHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Id
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    private Job jobId;

    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department departmentId;
}
