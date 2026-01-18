package com.svalero.asociation.service;

import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.SocioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.Character.getType;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocioServiceTest {

    @InjectMocks
    private SocioService socioService;

    @Mock
    private SocioRepository socioRepository;

    @Mock
    private ModelMapper mapper;



    @Test
    public void testFindAll() {

        List<Socio> mockSocioList=List.of(
                new Socio(1,"77777777U","Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323","Nuclear",true, LocalDate.now(), null, null),
                new Socio(2,"77777777U","Yolanda", "Del Valle", "email@email.com", "C Octavio Cuartero 35c","888-566-323", "Monoparental", true, LocalDate.now() ,null, null));

        List<SocioDto> mockSocioDtoList=List.of(
                new SocioDto(1L,"77777777U","Marcos", "García", "email@email.com", "888-566-323", true, LocalDate.now()),
                new SocioDto(2L,"77787777U","Yolanda", "Del Valle", "email@email.com", "888-566-323", true,LocalDate.now()));

        when(socioRepository.findAll()).thenReturn(mockSocioList);
        when(mapper.map(mockSocioList, new TypeToken<List<SocioDto>>(){}.getType())).thenReturn(mockSocioDtoList);

        List<SocioDto> socioDtoList = socioService.findAll(null, "", null);
        assertEquals(2, socioDtoList.size());
        assertEquals("77777777U", socioDtoList.getFirst().getDni());
        assertEquals("Yolanda", socioDtoList.get(1).getName());

        verify(socioRepository, times(1)).findAll();
        verify(socioRepository, times(0)).findByFamilyModel("");
        verify(socioRepository, times(0)).findByActive(null);
        verify(socioRepository, times(0)).findByEntryDateAfter(null);
    };

    @Test
    public void testFindAllByentryDate() {

        List<Socio> mockSocioList=List.of(
                new Socio(2,"77777777U","Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323","Nuclear",true, LocalDate.now().plusDays(1), null, null),
                new Socio(3,"77777777U","Yolanda", "Del Valle", "email@email.com", "C Octavio Cuartero 35c","888-566-323", "Monoparental", true, LocalDate.now().plusDays(1) ,null, null));

        List<SocioDto> mockSocioDtoList=List.of(
                new SocioDto(1L,"77777777U","Marcos", "García", "email@email.com", "888-566-323", true, LocalDate.now()),
                new SocioDto(2L,"77787777U","Yolanda", "Del Valle", "email@email.com", "888-566-323", true, LocalDate.now().plusDays(1)));

        when(socioRepository.findByEntryDateAfter(LocalDate.now())).thenReturn(mockSocioList);
        when(mapper.map(mockSocioList, new TypeToken<List<SocioDto>>(){}.getType())).thenReturn(mockSocioDtoList);

        List<SocioDto> socioDtoList = socioService.findAll(LocalDate.now(), "", null);

        assertEquals(2, socioDtoList.size());
        assertEquals("77777777U", socioDtoList.getFirst().getDni());
        assertEquals(LocalDate.now().plusDays(1), socioDtoList.get(1).getEntryDate());

        verify(socioRepository, times(0)).findAll();
        verify(socioRepository, times(0)).findByFamilyModel("");
        verify(socioRepository, times(0)).findByActive(null);
        verify(socioRepository, times(1)).findByEntryDateAfter(LocalDate.now());
    };



    @Test
    void testAdd() {
        Socio mockNewSocio = new Socio(3,"99932405D","Oscar", "Lanuza", "email@email.com", "C Subida 128", "991-003-323","Monoparental",false, LocalDate.now().plusDays(1), null, null);
        when(socioRepository.save(any(Socio.class))).thenReturn(mockNewSocio);

        Socio result = socioService.add(mockNewSocio);

        assertEquals("Oscar", result.getName());

        verify(socioRepository, times(1)).save(mockNewSocio);
    }

    @Test
    void testModify() {

        Socio mockSocio = new Socio(3,"77732405D","Eduardo", "Lanuza", "email@email.com", "C Subida 128", "991-003-323","Monoparental",false, LocalDate.now().plusDays(1), null, null);
            when(socioRepository.findById(anyLong())).thenReturn(Optional.of(mockSocio));

        SocioDto mappedMockSocio = socioService.findById(mockSocio.getId());

        Socio updatedMockSocio = new Socio(3,"77732405D","Jorge", "Lanuza", "email@email.com", "C Subida 128", "991-003-323","Monoparental",false, LocalDate.now().plusDays(1), null, null);
        when(socioRepository.save(any(Socio.class))).thenReturn(updatedMockSocio);

        Socio result = socioService.modify(3, mockSocio);

        assertNotEquals(mockSocio.getName(), result.getName());
        verify(socioRepository, times(1)).save(any(Socio.class));
    }


}