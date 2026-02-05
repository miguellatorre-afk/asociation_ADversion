package com.svalero.asociation.service;

import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.ParticipanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipanteServiceTest {
    @InjectMocks
    private ParticipanteService participanteService;

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testFindAll() {

        Socio socio = new Socio();
        socio.setId(1L);

        Participante p1 = new Participante();
        p1.setId(1L);
        p1.setName("Alberto");
        p1.setSocio(socio);

        Participante p2 = new Participante();
        p2.setId(2L);
        p2.setName("Roberto");
        p2.setSocio(socio);

        when(participanteRepository.findByFilters(null, null, null))
                .thenReturn(List.of(p1, p2));

        ParticipanteDto dto1 = new ParticipanteDto();
        dto1.setId(1L);
        dto1.setName("Alberto");

        ParticipanteDto dto2 = new ParticipanteDto();
        dto2.setId(2L);
        dto2.setName("Roberto");

        when(mapper.map(p1, ParticipanteDto.class)).thenReturn(dto1);
        when(mapper.map(p2, ParticipanteDto.class)).thenReturn(dto2);


        List<ParticipanteDto> res = participanteService.findAll(null, null, null);


        assertEquals(2, res.size());
        assertEquals("Alberto", res.get(0).getName());
        assertEquals(1L, res.get(0).getSocioID());
        assertEquals(1L, res.get(1).getSocioID());


        verify(participanteRepository).findByFilters(null, null, null);
        verify(mapper).map(p1, ParticipanteDto.class);
        verify(mapper).map(p2, ParticipanteDto.class);
    }


    @Test
    public void testFindByBirthDateAfter() {

        Socio socio = new Socio();
        socio.setId(1L);

        Participante p1 = new Participante();
        p1.setId(1L);
        p1.setName("Alberto");
        p1.setSocio(socio);

        Participante p2 = new Participante();
        p2.setId(2L);
        p2.setName("Roberto");
        p2.setSocio(socio);

        when(participanteRepository.findByFilters(LocalDate.now().minusYears(20), null, null))
                .thenReturn(List.of(p1, p2));

        ParticipanteDto dto1 = new ParticipanteDto();
        dto1.setId(1L);
        dto1.setName("Alberto");

        ParticipanteDto dto2 = new ParticipanteDto();
        dto2.setId(2L);
        dto2.setName("Roberto");

        when(mapper.map(p1, ParticipanteDto.class)).thenReturn(dto1);
        when(mapper.map(p2, ParticipanteDto.class)).thenReturn(dto2);

        List<ParticipanteDto> res = participanteService.findAll(LocalDate.now().minusYears(20), null, null);

//        assertEquals(2, participanteDtosList.size());
//        assertEquals("Alberto", participanteDtosList.getFirst().getName());
//
//        verify(participanteRepository, times(1)).findByFilters(LocalDate.now().plusDays(20), null, null  );

    }

    @Test
    public void testfindByNameStartingWithIgnoreCase() {

        Socio socio = new Socio();
        socio.setId(1L);

        Participante p1 = new Participante();
        p1.setId(1L);
        p1.setName("Alberto");
        p1.setSocio(socio);

        Participante p2 = new Participante();
        p2.setId(2L);
        p2.setName("Roberto");
        p2.setSocio(socio);

        when(participanteRepository.findByFilters(null, "Roberto", null))
                .thenReturn(List.of(p1, p2));

        ParticipanteDto dto1 = new ParticipanteDto();
        dto1.setId(1L);
        dto1.setName("Alberto");

        ParticipanteDto dto2 = new ParticipanteDto();
        dto2.setId(2L);
        dto2.setName("Roberto");

        when(mapper.map(p1, ParticipanteDto.class)).thenReturn(dto1);
        when(mapper.map(p2, ParticipanteDto.class)).thenReturn(dto2);

        List<ParticipanteDto> participanteList = participanteService.findAll(null, "Roberto", null);

        assertEquals(2, participanteList.size());
        assertEquals("Alberto", participanteList.getFirst().getName());

        verify(participanteRepository, times(1)).findByFilters(null, "Roberto", null);


    }

    @Test
    public void testfindBytypeRel() {
        Socio socio = new Socio();
        socio.setId(1L);

        Participante p1 = new Participante();
        p1.setId(1L);
        p1.setName("Alberto");
        p1.setSocio(socio);

        Participante p2 = new Participante();
        p2.setId(2L);
        p2.setName("Roberto");
        p2.setSocio(socio);

        when(participanteRepository.findByFilters(null, null, "hijo"))
                .thenReturn(List.of(p1, p2));

        ParticipanteDto dto1 = new ParticipanteDto();
        dto1.setId(1L);
        dto1.setName("Alberto");

        ParticipanteDto dto2 = new ParticipanteDto();
        dto2.setId(2L);
        dto2.setName("Roberto");

        when(mapper.map(p1, ParticipanteDto.class)).thenReturn(dto1);
        when(mapper.map(p2, ParticipanteDto.class)).thenReturn(dto2);

        List<ParticipanteDto>  participantesList = participanteService.findAll(null, null, "hijo");
        assertEquals(2, participantesList.size());
        assertEquals("Alberto", participantesList.getFirst().getName());

        verify(participanteRepository, times(1)).findByFilters(null, null, "hijo");
    }

    @Test
    public void testFindById(){

        ParticipanteDto selectedParticipanteDto =  new ParticipanteDto(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20), "ninguna", "hijo", 1);
        Participante selectedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.findById(1L)).thenReturn(Optional.of(selectedParticipante));

        when(mapper.map(selectedParticipante, ParticipanteDto.class)).thenReturn(selectedParticipanteDto);

        ParticipanteDto result = participanteService.findById(selectedParticipante.getId());

        assertEquals("Alberto", result.getName());
    }

    @Test
    public void testAdd(){
        Participante newparticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.save(newparticipante)).thenReturn(newparticipante);
        Participante result = participanteService.add(newparticipante);

        assertEquals("Alberto", result.getName());
        verify(participanteRepository, times(1)).save(newparticipante);
    }

    @Test
    public void testModify(){

        long id = 1L;

        ParticipanteDto participanteDto = new ParticipanteDto(
                1L, "77777777U", "Alberto", "Gomara",
                "email@email.com", "888-566-323",
                LocalDate.of(2000, 1, 1), "ninguna", "hijo", 1L
        );

        Participante old = new Participante();
        old.setId(id);

        when(participanteRepository.findById(id)).thenReturn(Optional.of(old));

        doNothing().when(mapper).map(any(ParticipanteDto.class), any(Participante.class));

        when(participanteRepository.save(any(Participante.class))).thenAnswer(inv -> inv.getArgument(0));


        Participante result = participanteService.modifyDto(id, participanteDto);

        assertNotNull(result);
        assertSame(old, result); // devuelve el mismo objeto que se guardó

        verify(participanteRepository).findById(id);
        verify(mapper).map(eq(participanteDto), eq(old));
        verify(participanteRepository).save(eq(old));
        verifyNoMoreInteractions(participanteRepository, mapper);

    }

    @Test
    public void testDelete(){
        Participante participante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));

        participanteService.delete(participante.getId());

        verify(participanteRepository, times(1)).delete(participante);

    }
}
