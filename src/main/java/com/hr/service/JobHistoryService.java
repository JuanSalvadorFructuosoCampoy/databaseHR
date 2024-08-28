package com.hr.service;

import com.hr.dto.JobHistoryDTO;
import com.hr.entity.JobHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface JobHistoryService {
    Page<JobHistoryDTO> listar(Pageable pageable);

    Page<JobHistoryDTO> listarPorId(Integer id,Pageable pageable);

    void save(JobHistory jobHistory);
}
