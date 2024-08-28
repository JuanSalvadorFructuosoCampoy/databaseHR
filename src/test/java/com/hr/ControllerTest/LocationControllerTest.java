package com.hr.ControllerTest;

import com.hr.dto.LocationDTO;
import com.hr.exception.LocationNoEncontradoException;
import com.hr.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setLocationId(10);
        location.setStreetAddress("1297 Via Cola di Rie");
        location.setPostalCode("00989");
        location.setCity("Roma");
        location.setStateProvince("Lazio");
        location.setCountryName("Italy");

        when(locationService.porId(10)).thenReturn(Optional.of(location));
        mockMvc.perform(get("/locations/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locationId").value(10));
    }

    @Test
    void testPorIdNoEncontrado() throws Exception {
        when(locationService.porId(10)).thenReturn(Optional.empty());
        mockMvc.perform(get("/locations/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setLocationId(10);
        location.setStreetAddress("1297 Via Cola di Rie");
        location.setPostalCode("00989");
        location.setCity("Roma");
        location.setStateProvince("Lazio");
        location.setCountryName("Italy");

        when(locationService.save(location)).thenReturn(location);
        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationId\":10,\"streetAddress\":\"1297 Via Cola di Rie\",\"postalCode\":\"00989\",\"city\":\"Roma\",\"stateProvince\":\"Lazio\",\"country\":\"Italy\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setLocationId(10);
        location.setStreetAddress("1297 Via Cola di Rie");
        location.setPostalCode("00989");
        location.setCity("Roma");
        location.setStateProvince("Lazio");
        location.setCountryName("Italy");

        when(locationService.save(location)).thenReturn(location);
        when(locationService.porId(10)).thenReturn(Optional.ofNullable(location));
        mockMvc.perform(put("/locations/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationId\":10,\"streetAddress\":\"1297 Via Cola di Rie\",\"postalCode\":\"00989\",\"city\":\"Roma\",\"stateProvince\":\"Lazio\",\"country\":\"Italy\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateException() throws Exception {
        when(locationService.save(any(LocationDTO.class))).thenThrow(LocationNoEncontradoException.class);

        mockMvc.perform(put("/locations/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationId\":10}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        mockMvc.perform(delete("/locations/10"))
                .andExpect(status().isNoContent());

        verify(locationService).eliminar(10);
    }
}
