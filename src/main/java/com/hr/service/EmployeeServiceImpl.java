package com.hr.service;

import com.hr.exception.*;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import com.hr.dto.EmployeeDTO;
import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> listar(Float minSalary, Date hireDate, Pageable pageable) {

        //Establecemos el filtro de salario mínimo, poniendo como condición que el salario sea mayor que el salario mínimo
        //Dicho salario mínimo se establecerá en la URL de la petición GET, por ejemplo, si se quiere filtrar por un salario mínimo de 1000,
        //se añadirá a la URL el parámetro minSalary=1000. Si no se añade este parámetro, se devolverán todos los registros.
        Specification<Employee> spec = (root, query, cb) -> {
           Predicate predicate = cb.conjunction();
            if (minSalary != null) {
                predicate = cb.and(predicate, cb.greaterThan(root.get("salary"), minSalary));
            }

            if (hireDate != null) {
                predicate = cb.and(predicate, cb.greaterThan(root.get("hireDate"), hireDate));
            }
            return predicate;
        };
        return employeeRepository.findAll(spec, pageable).map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> porId(int id) throws EmpleadoNoEncontradoException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmpleadoNoEncontradoException("No existe el empleado con id: " + id);
        }
        return employee.map(value -> modelMapper.map(value, EmployeeDTO.class));
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws MultipleException {
        MultipleException multipleException = new MultipleException();
        Employee employee = new Employee();
        if (employeeDTO.getEmployeeId() != null) {
            // Si el ID es válido, estamos editando un registro existente.
            // Primero, obtenemos el registro existente.
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeDTO.getEmployeeId());
            if (existingEmployee.isPresent()) {
                // Si el registro existe, mapeamos el DTO al registro existente.
                employee = modelMapper.map(employeeDTO, Employee.class);
                // Aseguramos que el ID se mantiene igual.
                employee.setEmployeeId(existingEmployee.get().getEmployeeId());
            } else {
                // Si el registro no existe, lanzamos una excepción.
                multipleException.addException(new EmpleadoNoEncontradoException("No existe el empleado con ID " + employeeDTO.getEmployeeId() + "."));
            }
        } else {
            // Si el ID no es válido, estamos creando un nuevo registro.
            employee = modelMapper.map(employeeDTO, Employee.class);
        }

        if (employeeRepository.findByJobTitle(employeeDTO.getJobName()) != null) {
            employee.setJob(employeeRepository.findByJobTitle(employeeDTO.getJobName()));
        } else {
            multipleException.addException(new TrabajoNoEncontradoException("No existe el trabajo con nombre: " + employeeDTO.getJobName() + "."));
        }

        if (employeeRepository.findByManagerName(employeeDTO.getManager()) != null) {
            employee.setManager(employeeRepository.findByManagerName(employeeDTO.getManager()));
        } else {
            multipleException.addException(new ManagerNoEncontradoException("No existe el manager con nombre: " + employeeDTO.getManager() + "."));
        }

        if (employeeRepository.findByDepartmentName(employeeDTO.getDepartment()) != null) {
            employee.setDepartment(employeeRepository.findByDepartmentName(employeeDTO.getDepartment()));
        } else {
            multipleException.addException(new DepartamentoNoEncontradoException("No existe el departamento con nombre: " + employeeDTO.getDepartment() + "."));
        }
        if (employeeDTO.getEmail().contains("@")) {
            employee.setEmail(employeeDTO.getEmail().substring(0, employeeDTO.getEmail().indexOf("@")).toUpperCase());
        } else {
            employee.setEmail(employeeDTO.getEmail().toUpperCase());
        }
        if(employeeRepository.findByEmail(employee.getEmail()) != null && !employeeRepository.findByEmail(employee.getEmail()).getEmployeeId().equals(employee.getEmployeeId())){
            multipleException.addException(new EmailDuplicadoException("El email ya existe."));
        }
        if (multipleException.hasExceptions()) {
            throw multipleException;
        }
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    @Transactional
    public void eliminar(int id) throws EmpleadoNoEncontradoException {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new EmpleadoNoEncontradoException("No existe el empleado con id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
