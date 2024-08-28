package com.hr.ControllerTest;

import com.hr.dto.RegionDTO;
import com.hr.entity.Region;
import com.hr.exception.RegionNoEncontradoException;
import com.hr.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegionService regionService;

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/regions"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        RegionDTO region = new RegionDTO();
        region.setRegionId(10);
        region.setRegionName("Europe");

        when(regionService.porId(10)).thenReturn(Optional.of(region));

        mockMvc.perform(get("/regions/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.regionId").value(10));
    }

    @Test
    void testSave() throws Exception {
        Region region = new Region();
        region.setRegionId(1);
        region.setRegionName("Europe");
        when(regionService.save(region)).thenReturn(region);

        mockMvc.perform(post("/regions")
                        .contentType("application/json")
                        .content("{\"regionId\": 10,\"regionName\":\"Europe\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        Region region = new Region();
        region.setRegionId(1);
        region.setRegionName("Europe");

        RegionDTO regionDTO = new RegionDTO();
        region.setRegionId(1);
        region.setRegionName("Europe");
        when(regionService.save(region)).thenReturn(region);
        when(regionService.porId(1)).thenReturn(Optional.of(regionDTO));

        mockMvc.perform(put("/regions/1")
                        .contentType("application/json")
                        .content("{\"regionId\": 1,\"regionName\":\"Europe\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateException() throws Exception {
        when(regionService.save(any(Region.class))).thenThrow(RegionNoEncontradoException.class);

        mockMvc.perform(put("/regions/1")
                        .contentType("application/json")
                        .content("{\"regionId\": 1,\"regionName\":\"Europe\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Integer id = 1;
        doNothing().when(regionService).eliminar(id);

        mockMvc.perform(delete("/regions/" + id))
                .andExpect(status().isNoContent());

        verify(regionService, times(1)).eliminar(id);
    }
}
