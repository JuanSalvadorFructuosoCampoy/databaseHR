package com.hr.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@IdClass(JobHistoryDtoId.class)
@Entity
public class JobHistoryDTO {
    @Id
    private String employee;
    @Id
    private String startDate;
    private String jobName;
    private String endDate;
    private String department;
}
