package com.hr.dtoTest;

import com.hr.dto.CountryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class CountryDTOTest {

    @Test
    void testToString() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId("1");
        countryDTO.setCountryName("India");
        countryDTO.setRegion("Asia");
        assert countryDTO.toString().equals("CountryDTO(countryId=1, countryName=India, region=Asia)");
    }
}
