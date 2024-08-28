package com.hr.dtoTest;

import com.hr.dto.JobHistoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryDTOTest {

    @Test
    void setterTest() {
        JobHistoryDTO jobHistoryDTO = new JobHistoryDTO();
        jobHistoryDTO.setEmployee("1");
        jobHistoryDTO.setStartDate("2021-01-01");
        jobHistoryDTO.setJobName("Developer");
        jobHistoryDTO.setEndDate("2021-12-31");
        jobHistoryDTO.setDepartment("IT");
        assert jobHistoryDTO.getEmployee().equals("1");
        assert jobHistoryDTO.getStartDate().equals("2021-01-01");
        assert jobHistoryDTO.getJobName().equals("Developer");
        assert jobHistoryDTO.getEndDate().equals("2021-12-31");
        assert jobHistoryDTO.getDepartment().equals("IT");
    }

    @Test
    void testToString() {
        JobHistoryDTO jobHistoryDTO = new JobHistoryDTO();
        jobHistoryDTO.setEmployee("1");
        jobHistoryDTO.setStartDate("2021-01-01");
        jobHistoryDTO.setJobName("Developer");
        jobHistoryDTO.setEndDate("2021-12-31");
        jobHistoryDTO.setDepartment("IT");
        assertEquals("JobHistoryDTO(employee=1, startDate=2021-01-01, jobName=Developer, endDate=2021-12-31, department=IT)", jobHistoryDTO.toString());
    }
}
