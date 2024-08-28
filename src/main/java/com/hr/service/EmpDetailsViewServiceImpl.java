package com.hr.service;

import com.hr.entity.EmpDetailsView;
import com.hr.exception.EmpleadoNoEncontradoException;
import com.hr.repository.EmpDetailsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmpDetailsViewServiceImpl implements EmpDetailsViewService{

    private final EmpDetailsViewRepository empDetailsViewRepository;

    @Autowired
    public EmpDetailsViewServiceImpl(EmpDetailsViewRepository empDetailsViewRepository) {
        this.empDetailsViewRepository = empDetailsViewRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpDetailsView> listar(Pageable pageable) {
        return empDetailsViewRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpDetailsView> porId(Integer id) throws EmpleadoNoEncontradoException {
       Optional<EmpDetailsView> empDetailsView = empDetailsViewRepository.findById(id);
         if(empDetailsView.isEmpty()){
              throw new EmpleadoNoEncontradoException("No existe el empleado con id: " + id);
         }
         return empDetailsView;
    }
}
