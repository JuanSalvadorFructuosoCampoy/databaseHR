package com.hr.dtoTest;

import com.hr.dto.RegionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class RegionDTOTest {

    @Test
    void testToString() {
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setRegionId(1);
        regionDTO.setRegionName("Asia");
        assert regionDTO.toString().equals("RegionDTO(regionId=1, regionName=Asia, countries=null)");
    }
}
