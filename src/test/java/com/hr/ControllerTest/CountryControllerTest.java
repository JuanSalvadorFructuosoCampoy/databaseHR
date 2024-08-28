package com.hr.ControllerTest;

import com.hr.dto.CountryDTO;
import com.hr.entity.Country;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
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
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        countryDTO = new CountryDTO();
        countryDTO.setCountryId("ES");
        countryDTO.setCountryName("Spain");
        countryDTO.setRegion("Europe");
    }

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        when(countryService.porId("ES")).thenReturn(Optional.ofNullable(countryDTO));
        mockMvc.perform(get("/countries/ES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryId").value("ES"));
    }

    @Test
    void testPorIdNoEncontrado() throws Exception {
        when(countryService.porId("ES")).thenReturn(Optional.empty());
        mockMvc.perform(get("/countries/ES")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception {
        when(countryService.save(countryDTO)).thenReturn(countryDTO);
        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"countryId\":\"ES\",\"countryName\":\"Spain\",\"region\":\"Europe\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        CountryDTO country = new CountryDTO();
        country.setCountryId("ES");

        when(countryService.save(country)).thenReturn(country);
        when(countryService.porId("ES")).thenReturn(Optional.ofNullable(country));

        mockMvc.perform(put("/countries/ES")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"countryId\":\"ES\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateException() throws Exception {
        when(countryService.save(any(CountryDTO.class))).thenThrow(CountryNoEncontradoException.class);

        mockMvc.perform(put("/countries/ES")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"countryId\":\"ES\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        mockMvc.perform(delete("/countries/ES"))
                .andExpect(status().isNoContent());

        verify(countryService).eliminar("ES");

    }
}
