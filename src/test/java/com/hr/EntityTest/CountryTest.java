package com.hr.EntityTest;

import com.hr.entity.Country;
import com.hr.entity.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class CountryTest {

    @Test
    void toStringTest() {
        Country country = new Country();
        country.setCountryId("US");
        country.setCountryName("United States of America");
        country.setRegion(new Region());
        System.out.println(country.toString());
        assert country.toString().equals("Country(countryId=US, countryName=United States of America, region=Region(regionId=null, regionName=null, countries=null), locations=null)");
    }
}
