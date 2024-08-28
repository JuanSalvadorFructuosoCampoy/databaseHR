package com.hr.ServiceTest;

import com.hr.dto.LocationDTO;
import com.hr.entity.Country;
import com.hr.entity.Location;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.exception.LocationNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.repository.LocationRepository;
import com.hr.service.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class LocationServiceImplTest {

    @MockBean
    private LocationRepository locationRepository;

    @Mock
    private LocationServiceImpl locationServiceImpl;

    @MockBean
    @Qualifier("locationModelMapper")
    private ModelMapper locationModelMapper;

    @BeforeEach
    void setUp() {
        locationServiceImpl = new LocationServiceImpl(locationRepository, locationModelMapper);
    }

    @Test
    void testListar() {
        Location location = new Location();
        location.setLocationId(1);

        Location location2 = new Location();
        location.setLocationId(2);

        List<Location> locationList = List.of(location, location2);
        Page<Location> locationPage = new PageImpl<>(locationList);

        when(locationRepository.findAll(any(Pageable.class))).thenReturn(locationPage);

        Page<LocationDTO> result = locationServiceImpl.listar(Pageable.unpaged());

        assertEquals(locationPage.map(locat -> locationModelMapper.map(locat, LocationDTO.class)), result);
    }

    @Test
    void testPorId() throws LocationNoEncontradoException {
        Location location = new Location();
        location.setLocationId(1);

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);

        when(locationRepository.findById(1)).thenReturn(Optional.of(location));
        when(locationModelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        LocationDTO result = locationServiceImpl.porId(1).get();

        assertEquals(locationModelMapper.map(location, LocationDTO.class), result);
    }

    @Test
    void testPorIdException() {
        when(locationRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(LocationNoEncontradoException.class, () -> locationServiceImpl.porId(1));
    }

    @Test
    void testSave() throws MultipleException, CountryNoEncontradoException, LocationNoEncontradoException {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);
        locationDTO.setCountryName("Argentina");
        locationDTO.setCity("Buenos Aires");
        locationDTO.setStreetAddress("Av. Corrientes 1234");
        locationDTO.setPostalCode("C1043AAZ");
        locationDTO.setStateProvince("Buenos Aires");

        Location location = new Location();
        location.setLocationId(1);

        when(locationModelMapper.map(locationDTO, Location.class)).thenReturn(location);
        when(locationRepository.findById(anyInt())).thenReturn(Optional.of(location));
        when(locationRepository.findByCountryName(anyString())).thenReturn(new Country());
        when(locationRepository.save(location)).thenReturn(location);
        when(locationModelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        LocationDTO result = locationServiceImpl.save(locationDTO);

        assertEquals(locationModelMapper.map(location, LocationDTO.class), result);
    }

    @Test
    void testSaveLocationNoEncontrado() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);
        locationDTO.setCountryName("Argentina");
        locationDTO.setCity("Buenos Aires");
        locationDTO.setStreetAddress("Av. Corrientes 1234");
        locationDTO.setPostalCode("C1043AAZ");
        locationDTO.setStateProvince("Buenos Aires");

        Location location = new Location();
        location.setLocationId(1);

        when(locationModelMapper.map(locationDTO, Location.class)).thenReturn(location);
        when(locationRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(locationRepository.findByCountryName(anyString())).thenReturn(new Country());
        when(locationRepository.save(location)).thenReturn(location);
        when(locationModelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        assertThrows(MultipleException.class, () -> locationServiceImpl.save(locationDTO));
    }

    @Test
    void testSaveCountryNoEncontrado() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);
        locationDTO.setCountryName("Argentina");
        locationDTO.setCity("Buenos Aires");
        locationDTO.setStreetAddress("Av. Corrientes 1234");
        locationDTO.setPostalCode("C1043AAZ");
        locationDTO.setStateProvince("Buenos Aires");

        Location location = new Location();
        location.setLocationId(1);

        when(locationModelMapper.map(locationDTO, Location.class)).thenReturn(location);
        when(locationRepository.findById(anyInt())).thenReturn(Optional.of(location));
        when(locationRepository.findByCountryName(anyString())).thenReturn(null);
        when(locationRepository.save(location)).thenReturn(location);
        when(locationModelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        assertThrows(MultipleException.class, () -> locationServiceImpl.save(locationDTO));
    }

    @Test
    void testSaveCountryExceptions() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1);
        locationDTO.setCountryName("Argentina");
        locationDTO.setCity("");
        locationDTO.setStreetAddress("");
        locationDTO.setPostalCode("");
        locationDTO.setStateProvince("");

        Location location = new Location();
        location.setLocationId(1);

        when(locationModelMapper.map(locationDTO, Location.class)).thenReturn(location);
        when(locationRepository.findById(anyInt())).thenReturn(Optional.of(location));
        when(locationRepository.findByCountryName(anyString())).thenReturn(null);
        when(locationRepository.save(location)).thenReturn(location);
        when(locationModelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        assertThrows(MultipleException.class, () -> locationServiceImpl.save(locationDTO));
    }

    @Test
    void testEliminar() throws LocationNoEncontradoException {
        when(locationRepository.findById(anyInt())).thenReturn(Optional.of(new Location()));

        locationServiceImpl.eliminar(1);
    }

    @Test
    void testEliminarException() {
        when(locationRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(LocationNoEncontradoException.class, () -> locationServiceImpl.eliminar(1));
    }

}
