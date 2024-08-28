package com.hr.ServiceTest;

import com.hr.dto.RegionDTO;
import com.hr.entity.Region;
import com.hr.exception.RegionDuplicadoException;
import com.hr.exception.RegionNoEncontradoException;
import com.hr.repository.RegionRepository;
import com.hr.service.RegionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RegionServiceImplTest {

    @MockBean
    private RegionRepository regionRepository;

    @MockBean
    private RegionServiceImpl regionServiceImpl;

    @MockBean
    private RegionDTO regionDTO;

    @MockBean
    @Qualifier("regionModelMapper")
    private ModelMapper regionModelMapper;

    @BeforeEach
    void setUp() {
        regionRepository = Mockito.mock(RegionRepository.class);
        regionServiceImpl = new RegionServiceImpl(regionRepository, regionModelMapper);
    }

    @Test
    void testListar() {
    Region region1 = new Region();
    region1.setRegionId(1);
    Region region2 = new Region();
    region2.setRegionId(2);

    List<Region> regionList = Arrays.asList(region1, region2);

    Page<Region> regionPage = new PageImpl<>(regionList);

        when(regionRepository.findAll(any(Pageable.class))).thenReturn(regionPage);

        Page<RegionDTO> result = regionServiceImpl.listar(Pageable.unpaged());

        assertEquals(regionPage.map(region -> regionModelMapper.map(region, RegionDTO.class)), result);
    }

    @Test
    void testPorId() throws RegionNoEncontradoException {
        Region region = new Region();
        region.setRegionId(1);

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setRegionId(1);

        when(regionRepository.findById(any())).thenReturn(Optional.of(region));
        when(regionModelMapper.map(region, RegionDTO.class)).thenReturn(regionDTO);

        Optional<RegionDTO> result = regionServiceImpl.porId(1);

        assertEquals(Optional.of(regionDTO), result);
    }

    @Test
    void testPorIdException() {
        when(regionRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RegionNoEncontradoException.class, () -> regionServiceImpl.porId(1));
    }

    @Test
    void testSave() throws RegionDuplicadoException, RegionNoEncontradoException {
        Region region = new Region();
        region.setRegionId(1);

        when(regionRepository.save(region)).thenReturn(region);

        Region result = regionServiceImpl.save(region);

        assertEquals(result, region);
    }

    @Test
    void testSaveNull() throws RegionDuplicadoException, RegionNoEncontradoException {
        Region region = new Region();
        region.setRegionId(null);

        Region savedRegion = new Region();
        savedRegion.setRegionId(1);

        when(regionRepository.findMaxRegionId()).thenReturn(1);
        when(regionRepository.save(any(Region.class))).thenReturn(savedRegion);

        Region result = regionServiceImpl.save(region);

        assertEquals(result.getRegionId(), 1);
    }

    @Test
    void testEliminar() throws RegionNoEncontradoException {
        when(regionRepository.existsById(1)).thenReturn(true);
        regionServiceImpl.eliminar(1);
    }
}
