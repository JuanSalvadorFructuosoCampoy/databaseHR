package com.hr.repository;

import com.hr.entity.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobHistoryRepository extends JpaRepository<JobHistory, Integer>, JpaSpecificationExecutor<JobHistory> {
}
