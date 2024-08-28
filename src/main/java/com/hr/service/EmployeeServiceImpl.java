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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper employeeModelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper employeeModelMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeModelMapper = employeeModelMapper;
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
        return employeeRepository.findAll(spec, pageable).map(employee -> employeeModelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> porId(int id) throws EmpleadoNoEncontradoException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmpleadoNoEncontradoException("No existe el empleado con id: " + id);
        }
        return employee.map(value -> employeeModelMapper.map(value, EmployeeDTO.class));
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws MultipleException, ParseException {
        MultipleException multipleException = new MultipleException();
        if(employeeDTO.getFirstName() == null || employeeDTO.getFirstName().isEmpty()){
            multipleException.addException(new FirstNameNoEncontradoException("El nombre no puede estar vacío."));
        }
        if(employeeDTO.getLastName() == null || employeeDTO.getLastName().isEmpty()){
            multipleException.addException(new LastNameNoEncontradoException("El apellido no puede estar vacío."));
        }
        if(employeeDTO.getEmail() == null || employeeDTO.getEmail().isEmpty()){
            multipleException.addException(new EmailNoEncontradoException("El email no puede estar vacío."));
        }
        if(employeeDTO.getPhoneNumber() == null || employeeDTO.getPhoneNumber().isEmpty()){
            multipleException.addException(new PhoneNumberNoEncontradoException("El teléfono no puede estar vacío."));
        }
        if(employeeDTO.getHireDate() == null || employeeDTO.getHireDate().isEmpty()){
            multipleException.addException(new HireDateNoEncontradoException("La fecha de contratación no puede estar vacía."));
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date hireDate = dateFormat.parse(employeeDTO.getHireDate());
            } catch (ParseException e) {
                multipleException.addException(new HireDateNoEncontradoException("La fecha de contratación debe estar en el formato dd/MM/yyyy."));
            }
        }
        if(employeeDTO.getSalary() <= 0) {
            multipleException.addException(new SueldosIncorrectosException("El salario no puede ser menor o igual a 0."));
        }
        if(multipleException.hasExceptions()) {
            throw multipleException;
        }
        Employee employee = new Employee();
        if (employeeDTO.getEmployeeId() != null) {
            // Si el ID es válido, estamos editando un registro existente.
            // Primero, obtenemos el registro existente.
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeDTO.getEmployeeId());
            if (existingEmployee.isPresent()) {
                // Si el registro existe, mapeamos el DTO al registro existente.
                employee = employeeModelMapper.map(employeeDTO, Employee.class);
                // Aseguramos que el ID se mantiene igual.
                employee.setEmployeeId(existingEmployee.get().getEmployeeId());
            } else {
                // Si el registro no existe, lanzamos una excepción.
                multipleException.addException(new EmpleadoNoEncontradoException("No existe el empleado con ID " + employeeDTO.getEmployeeId() + "."));
            }

        } else {
            // Si el ID no es válido, estamos creando un nuevo registro.
            employee = employeeModelMapper.map(employeeDTO, Employee.class);
        }

        if (employeeRepository.findByJobTitle(employeeDTO.getJobName()) != null) {
            employee.setJob(employeeRepository.findByJobTitle(employeeDTO.getJobName()));
                if(employeeRepository.findByJobName(employeeDTO.getJobName()).getMinSalary() > employeeDTO.getSalary() || employeeRepository.findByJobName(employeeDTO.getJobName()).getMaxSalary() < employeeDTO.getSalary()){
                    multipleException.addException(new SueldosIncorrectosException("El salario no está dentro del rango permitido para el trabajo. El sueldo debe estar entre :" + employeeRepository.findByJobName(employeeDTO.getJobName()).getMinSalary() + " y " + employeeRepository.findByJobName(employeeDTO.getJobName()).getMaxSalary() + "."));
                }
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
        if(employeeDTO.getCommissionPct() == 0) {
            employee.setCommissionPct(null);
        }
        employeeRepository.save(employee);
        return employeeModelMapper.map(employee, EmployeeDTO.class);
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
