package com.hr.dtoTest;

import com.hr.dto.JobHistoryDtoId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryDtoIdTest {

    @Test
    void setterTest(){
        JobHistoryDtoId jobHistoryDTOId = new JobHistoryDtoId();
        jobHistoryDTOId.setEmployee("1");
        jobHistoryDTOId.setStartDate("2021-01-01");
        assert jobHistoryDTOId.getEmployee().equals("1");
        assert jobHistoryDTOId.getStartDate().equals("2021-01-01");
    }

    @Test
    void equalsAndHashCodeTest() {
        JobHistoryDtoId jobHistoryDTOId1 = new JobHistoryDtoId();
        JobHistoryDtoId jobHistoryDTOId2 = new JobHistoryDtoId();
        assert jobHistoryDTOId1.equals(jobHistoryDTOId2);
        assert jobHistoryDTOId1.hashCode() == jobHistoryDTOId2.hashCode();

    }
}
