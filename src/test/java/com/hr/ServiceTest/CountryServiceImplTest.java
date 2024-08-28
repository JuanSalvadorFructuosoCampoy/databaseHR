package com.hr.ServiceTest;

import com.hr.dto.CountryDTO;
import com.hr.entity.Country;
import com.hr.entity.Region;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.exception.NombrePaisNoEncontradoException;
import com.hr.repository.CountryRepository;
import com.hr.service.CountryServiceImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CountryServiceImplTest {

    @MockBean
    private CountryRepository countryRepository;

    @Mock
    private CountryServiceImpl countryServiceImpl;

    @MockBean
    private CountryDTO countryDTO;

    @MockBean
    @Qualifier("countryModelMapper")
    private ModelMapper countryModelMapper;

    @BeforeEach
    void setUp() {
        countryServiceImpl = new CountryServiceImpl(countryRepository, countryModelMapper);
    }

    @Test
    void testConstructor() {
        assertNotNull(countryServiceImpl);
    }

    @Test
    void testListar() {
        // 1. Crear algunos objetos Country y agregarlos a una lista
        Country country1 = new Country();
        country1.setCountryId("AR");
        country1.setCountryName("Argentina");
        country1.setRegion(new Region());

        Country country2 = new Country();
        country2.setCountryId("BR");
        country2.setCountryName("Brasil");
        country2.setRegion(new Region());

        List<Country> countryList = Arrays.asList(country1, country2);

        // 2. Crear una Page de Country a partir de esa lista
        Page<Country> countryPage = new PageImpl<>(countryList);

        // 3. Configurar el comportamiento de mock para countryRepository.findAll() para que devuelva nuestra Page de Country
        when(countryRepository.findAll(any(Pageable.class))).thenReturn(countryPage);

        // 4. Llamar al método listar() y obtener el resultado
        Page<CountryDTO> result = countryServiceImpl.listar(Pageable.unpaged());

        // 5. Verificar que el resultado contiene los objetos CountryDTO correctos
        assertEquals(countryPage.map(country -> countryModelMapper.map(country, CountryDTO.class)), result);
    }

    @Test
    void testPorId() throws CountryNoEncontradoException {
        Country country = new Country();
        country.setCountryId("AR");
        country.setCountryName("Argentina");
        country.setRegion(new Region());

        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId("AR");
        countryDTO.setCountryName("Argentina");
        countryDTO.setRegion("America");

        when(countryRepository.findById(anyString())).thenReturn(Optional.of(country));
        when(countryModelMapper.map(country, CountryDTO.class)).thenReturn(countryDTO);

        Optional<CountryDTO> result = countryServiceImpl.porId("AR");

        assertEquals(Optional.of(countryDTO), result);
    }

    @Test
    void testPorIdNotFound() {
        when(countryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(CountryNoEncontradoException.class, () -> countryServiceImpl.porId("AR"));
    }

    @Test
    void testSave() throws MultipleException, NombrePaisNoEncontradoException {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId("AR");
        countryDTO.setCountryName("Argentina");
        countryDTO.setRegion("America");

        Country country = new Country();
        country.setCountryId("AR");
        country.setCountryName("Argentina");
        country.setRegion(new Region());

        when(countryModelMapper.map(countryDTO, Country.class)).thenReturn(country);
        when(countryRepository.findById(anyString())).thenReturn(Optional.of(country));
        when(countryRepository.findByRegionName(anyString())).thenReturn(new Region());
        when(countryRepository.save(country)).thenReturn(country);
        when(countryModelMapper.map(country, CountryDTO.class)).thenReturn(countryDTO);

        CountryDTO result = countryServiceImpl.save(countryDTO);

        assertEquals(countryDTO, result);
    }

    @Test
    void testSaveCountryNoEncontrado() throws MultipleException {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId("AR");
        countryDTO.setCountryName("Argentina");
        countryDTO.setRegion("America");

        Country country = new Country();
        country.setCountryId("AR");
        country.setCountryName("Argentina");
        country.setRegion(new Region());

        when(countryModelMapper.map(countryDTO, Country.class)).thenReturn(country);
        when(countryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(MultipleException.class, () -> countryServiceImpl.save(countryDTO));
    }

    @Test
    void testSaveCountryNombrePaisNoEncontrado() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId("AR");
        countryDTO.setCountryName("");
        countryDTO.setRegion("America");

        Country country = new Country();
        country.setCountryId("AR");
        country.setCountryName("");
        country.setRegion(new Region());

        when(countryModelMapper.map(countryDTO, Country.class)).thenReturn(country);
        when(countryRepository.findById(anyString())).thenReturn(Optional.of(country));

        assertThrows(MultipleException.class, () -> countryServiceImpl.save(countryDTO));
    }

//    @Test
//    void testSaveCountryExists() throws MultipleException, NombrePaisNoEncontradoException {
//        CountryDTO countryDTO = new CountryDTO();
//        countryDTO.setCountryName("Spain");
//        Country country = new Country();
//        countryDTO.setRegion("Region");
//        when(countryRepository.findById("SP")).thenReturn(Optional.of(new Country())); // Devolver un Country existente
//        when(countryModelMapper.map(countryDTO, Country.class)).thenReturn(country);
//        CountryDTO savedCountry = countryServiceImpl.save(countryDTO);
//        assertEquals("S1", savedCountry.getCountryId());
//    }

    @Test
    void testSaveIdNull() throws MultipleException, NombrePaisNoEncontradoException {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId(null);
        countryDTO.setCountryName("Argentina");
        countryDTO.setRegion("America");

        Country country = new Country();
        country.setCountryId(null);
        country.setCountryName("Argentina");
        country.setRegion(new Region());

        when(countryModelMapper.map(countryDTO, Country.class)).thenReturn(country);
        when(countryRepository.findById(anyString())).thenReturn(Optional.empty());
        when(countryRepository.findByRegionName(anyString())).thenReturn(new Region());
        when(countryRepository.save(country)).thenReturn(country);
        when(countryModelMapper.map(country, CountryDTO.class)).thenReturn(countryDTO);

        CountryDTO result = countryServiceImpl.save(countryDTO);

        assertEquals(countryDTO, result);
    }

    @Test
    void testEliminar() throws CountryNoEncontradoException {
        when(countryRepository.findById(anyString())).thenReturn(Optional.of(new Country()));

        countryServiceImpl.eliminar("AR");
    }

    @Test
    void testEliminarException() {
        when(countryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(CountryNoEncontradoException.class, () -> countryServiceImpl.eliminar("AR"));
    }
}
