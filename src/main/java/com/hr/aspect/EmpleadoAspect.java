package com.hr.aspect;

import com.hr.dto.EmployeeDTO;
import com.hr.entity.Employee;
import com.hr.entity.JobHistory;
import com.hr.repository.EmployeeRepository;
import com.hr.service.JobHistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Aspect
@Component
public class EmpleadoAspect {
    private JobHistoryService jobHistoryService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmpleadoAspect(JobHistoryService jobHistoryService, EmployeeRepository employeeRepository) {
        this.jobHistoryService = jobHistoryService;
        this.employeeRepository = employeeRepository;
    }

    @Pointcut("execution(* com.hr.service.EmployeeServiceImpl.save(..)) && args(com.hr.dto.EmployeeDTO)")
    private void paraActualizarPuesto() {}

    @Before("paraActualizarPuesto()")
    public void actualizarPuesto(JoinPoint joinPoint) throws ParseException {
        Object[] args = joinPoint.getArgs();
        EmployeeDTO employeeDTO = (EmployeeDTO) args[0];
        if(employeeDTO.getEmployeeId() == null || employeeRepository.findByJobTitle(employeeDTO.getJobName()) == null){
            return;
        }
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeDTO.getEmployeeId());
        if (existingEmployee.isPresent() && !employeeDTO.getJobName().equals(existingEmployee.get().getJob().getJobTitle())) {
            JobHistory joHistoryOld = new JobHistory();
            joHistoryOld.setEmployee(existingEmployee.get());
            LocalDate ayer = LocalDate.now().minusDays(1);
            Date date = Date.from(ayer.atStartOfDay(ZoneId.systemDefault()).toInstant());
            joHistoryOld.setEndDate(date);
            joHistoryOld.setJobId(employeeRepository.findByJobTitle(employeeDTO.getJobName()));
            joHistoryOld.setStartDate(existingEmployee.get().getHireDate());
            joHistoryOld.setDepartmentId(existingEmployee.get().getDepartment());
            jobHistoryService.save(joHistoryOld);

            JobHistory jobHistory = new JobHistory();
            jobHistory.setEmployee(existingEmployee.get());
            jobHistory.setStartDate(new Date());
            jobHistory.setJobId(existingEmployee.get().getJob());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jobHistory.setEndDate(sdf.parse("9999-12-31"));
            jobHistory.setDepartmentId(existingEmployee.get().getDepartment());
            jobHistoryService.save(jobHistory);
        }
    }
}
