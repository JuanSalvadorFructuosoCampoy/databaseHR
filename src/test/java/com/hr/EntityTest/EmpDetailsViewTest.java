package com.hr.EntityTest;

import com.hr.entity.EmpDetailsView;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class EmpDetailsViewTest {

    @Test
    void testToString() {
        EmpDetailsView empDetailsView = new EmpDetailsView();
        empDetailsView.setEmployeeId(1);
        empDetailsView.setJobId("Job");
        empDetailsView.setManagerId(100);
        empDetailsView.setDepartmentId(10);
        empDetailsView.setLocationId(1000);
        empDetailsView.setCountryId('U');
        empDetailsView.setFirstName("Juansa");
        empDetailsView.setLastName("Campoy");
        empDetailsView.setSalary(10000f);
        empDetailsView.setCommissionPct(null);
        empDetailsView.setDepartmentName("IT");
        empDetailsView.setJobTitle("Programmer");
        empDetailsView.setCity("City");
        empDetailsView.setStateProvince("Province");
        empDetailsView.setCountryName("Country");
        empDetailsView.setRegionName("Region");

        assertEquals("EmpDetailsView(employeeId=1, jobId=Job, managerId=100, departmentId=10, locationId=1000, countryId=U, firstName=Juansa, lastName=Campoy, salary=10000.0, commissionPct=null, departmentName=IT, jobTitle=Programmer, city=City, stateProvince=Province, countryName=Country, regionName=Region)", empDetailsView.toString());
    }
}
