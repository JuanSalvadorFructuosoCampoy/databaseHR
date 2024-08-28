package com.hr.service;

import com.hr.dto.DepartmentDTO;
import com.hr.dto.DepartmentDTOGet;
import com.hr.entity.Department;
import com.hr.exception.*;
import com.hr.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper departmentModelMapper;
    private final ModelMapper departmentDTOGetModelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper departmentModelMapper, ModelMapper departmentDTOGetModelMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentModelMapper = departmentModelMapper;
        this.departmentDTOGetModelMapper = departmentDTOGetModelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentDTOGet> listar(Pageable pageable) {
        return departmentRepository.findAll(pageable).map((element) -> departmentDTOGetModelMapper.map(element, DepartmentDTOGet.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDTOGet> porId(Integer id) throws DepartamentoNoEncontradoException {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            throw new DepartamentoNoEncontradoException("No se ha encontrado el departamento con id: " + id);
        }
        return department.map((element) -> departmentDTOGetModelMapper.map(element, DepartmentDTOGet.class));
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDto) throws MultipleException {
        MultipleException multipleException = new MultipleException();
        Department departamento = new Department();
        if(departmentDto.getDepartmentName().trim().isEmpty()) {
            multipleException.addException(new DepartmentNameNoEncontradoException("El nombre del departamento no puede estar vacío"));
        }
        if (departmentDto.getDepartmentId() != null) {
            Optional<Department> departmentExistente = departmentRepository.findById(departmentDto.getDepartmentId());
            if (departmentExistente.isPresent()) {
                departamento = departmentModelMapper.map(departmentDto, Department.class);
                departamento.setDepartmentId(departmentExistente.get().getDepartmentId());
            } else {
                multipleException.addException(new DepartamentoNoEncontradoException("No se ha encontrado el departamento con ID: " + departmentDto.getDepartmentId()));
            }

        } else {
            departamento = departmentModelMapper.map(departmentDto, Department.class);
        }
        if (departmentRepository.findByLocationId(departmentDto.getLocationId()) != null) {
            departamento.setLocation(departmentRepository.findByLocationId(departmentDto.getLocationId()));
        } else {
            multipleException.addException(new LocationNoEncontradoException("No se ha encontrado la localización con ID: " + departmentDto.getLocationId()));
        }

        departmentDto.setDepartmentName(departmentDto.getDepartmentName());
        if (departmentRepository.findByManagerName(departmentDto.getManager()) == null) {
            multipleException.addException(new ManagerNoEncontradoException("No se ha encontrado el manager de nombre: " + departmentDto.getManager()));
        } else {
            departamento.setManager(departmentRepository.findByManagerName(departmentDto.getManager()));
        }

        if (multipleException.hasExceptions()) {
            throw multipleException;
        }
        departmentRepository.save(departamento);
        return departmentModelMapper.map(departamento, DepartmentDTO.class);
    }

    @Override
    public void eliminar(Integer id) throws DepartamentoNoEncontradoException {
        if (departmentRepository.findById(id).isEmpty()) {
            throw new DepartamentoNoEncontradoException("No se ha encontrado el departamento con ID: " + id);
        }
        departmentRepository.deleteById(id);
    }


}
