package com.hr.dtoTest;

import com.hr.dto.LocationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class LocationDTOTest {

    @Test
    void testToString() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);
        locationDTO.setStreetAddress("Street");
        locationDTO.setPostalCode("PostalCode");
        locationDTO.setCity("City");
        locationDTO.setStateProvince("StateProvince");
        locationDTO.setCountryName("Country");
        assertEquals("LocationDTO(locationId=1, streetAddress=Street, postalCode=PostalCode, city=City, stateProvince=StateProvince, countryName=Country)", locationDTO.toString());
    }
}
