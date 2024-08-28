package com.hr.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
public class JobHistoryId implements Serializable {
    private Integer employee;
    private Date startDate;
}
