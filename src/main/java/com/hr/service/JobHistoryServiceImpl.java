package com.hr.service;


import com.hr.dto.JobHistoryDTO;
import com.hr.entity.JobHistory;
import com.hr.repository.JobHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;
    private final ModelMapper jobHistoryModelMapper;

    @Autowired
    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository, ModelMapper jobHistoryModelMapper) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistoryModelMapper = jobHistoryModelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobHistoryDTO> listar(Pageable pageable) {
        return jobHistoryRepository.findAll(pageable).map(jobHistory -> jobHistoryModelMapper.map(jobHistory, JobHistoryDTO.class));
    }

    @Override
    public Page<JobHistoryDTO> listarPorId(Integer id, Pageable pageable) {
        Specification<JobHistory> spec = (root,query,cb) -> {
            if(id != null){
                return cb.equal(root.get("employee").get("employeeId"),id);
            }
            return null;
        };
        return jobHistoryRepository.findAll(spec,pageable).map(jobHistory -> jobHistoryModelMapper.map(jobHistory, JobHistoryDTO.class));
    }

    @Override
    public void save(JobHistory jobHistory) {
        jobHistoryRepository.save(jobHistory);
    }

}
