package com.hr.EntityTest;

import com.hr.entity.Job;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class JobTest {

    @Test
    void testJobId() {
        Job job = new Job();
        job.setJobId("Job");
        assertEquals("Job", job.getJobId());
    }

    @Test
    void testJobTitle() {
        Job job = new Job();
        job.setJobTitle("HR");
        assertEquals("HR", job.getJobTitle());
    }

    @Test
    void testMinSalary() {
        Job job = new Job();
        job.setMinSalary(1000);
        assertEquals(1000, job.getMinSalary());
    }

    @Test
    void testLocationId() {
        Job job = new Job();
        job.setMaxSalary(20000);
        assertEquals(20000, job.getMaxSalary());
    }

    @Test
    void testToString() {
        Job job = new Job();
        job.setJobId("Job");
        job.setJobTitle("HR");
        job.setMinSalary(1000);
        job.setMaxSalary(2000);

        String expectedString = "Job(jobId=Job, jobTitle=HR, minSalary=1000.0, maxSalary=2000.0, employee=null, jobHistories=null)";
        assertEquals(expectedString, job.toString());
    }
}
