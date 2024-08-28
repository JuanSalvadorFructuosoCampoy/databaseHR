package com.hr.EntityTest;

import com.hr.entity.JobHistoryId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryIdTest {

    @Test
    void equalsAndHashCodeTest() {
        JobHistoryId jobHistoryId1 = new JobHistoryId();
        jobHistoryId1.setEmployee(1);
        jobHistoryId1.setStartDate(new Date("2021/10/10"));
        JobHistoryId jobHistoryId2 = new JobHistoryId();
        jobHistoryId2.setEmployee(1);
        jobHistoryId2.setStartDate(new Date("2021/10/10"));
        assert jobHistoryId1.equals(jobHistoryId2);
        assert jobHistoryId1.hashCode() == jobHistoryId2.hashCode();
    }

    @Test
    void setterTest() {
        JobHistoryId jobHistoryId = new JobHistoryId();
        jobHistoryId.setEmployee(1);
        jobHistoryId.setStartDate(new Date("2021/10/10"));
        assert jobHistoryId.getEmployee() == 1;
        assert jobHistoryId.getStartDate().equals(new Date("2021/10/10"));
    }
}
