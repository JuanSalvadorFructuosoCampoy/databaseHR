package com.hr.EntityTest;

import com.hr.entity.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class RegionTest {

    @Test
    void toStringTest() {
        Region region = new Region();
        region.setRegionId(1);
        region.setRegionName("Europe");
        assertEquals("Region(regionId=1, regionName=Europe, countries=null)", region.toString());
    }
}
