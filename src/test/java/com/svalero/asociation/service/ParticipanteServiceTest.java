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
        // Arrange
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

        // Act
        List<ParticipanteDto> res = participanteService.findAll(null, null, null);


        assertEquals(2, res.size());
        assertEquals("Alberto", res.get(0).getName());
        assertEquals(1L, res.get(0).getSocioID());
        assertEquals(1L, res.get(1).getSocioID());


        verify(participanteRepository).findByFilters(null, null, null);
        verify(mapper).map(p1, ParticipanteDto.class);
        verify(mapper).map(p2, ParticipanteDto.class);
    }


//    @Test
//    public void testFindByBirthDateAfter() {
//
//        Socio socio = new Socio();
//        socio.setId(1L);
//        List<Participante> mockParticipanteList = List.of(
//                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", socio, null, null),
//                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", socio, null, null)
//        );
//
//        when(participanteRepository.findByFilters(LocalDate.now().plusDays(20), null, null  )).thenReturn(mockParticipanteList);
//
//        List<Participante>  participanteDtosList = participanteService.findAll(LocalDate.now().plusDays(20), null, null);
//
//        assertEquals(2, participanteDtosList.size());
//        assertEquals("Alberto", participanteDtosList.getFirst().getName());
//
//        verify(participanteRepository, times(1)).findByFilters(LocalDate.now().plusDays(20), null, null  );
//
//    }
//
//    @Test
//    public void testfindByNameStartingWithIgnoreCase() {
//        List<Participante> mockParticipanteList = List.of(
//                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
//                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
//        );
//
//        when(participanteRepository.findByFilters(null, "Roberto", null)).thenReturn(mockParticipanteList);
//
//        List<Participante> participanteList = participanteService.findAll(null, "Roberto", null);
//
//        assertEquals(2, participanteList.size());
//        assertEquals("Alberto", participanteList.getFirst().getName());
//
//        verify(participanteRepository, times(1)).findByFilters(null, "Roberto", null);
//
//
//    }
//
//    @Test
//    public void testfindBytypeRel() {
//        List<Participante> mockParticipanteList = List.of(
//                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
//                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
//        );
//
//        when(participanteRepository.findByFilters(null, null, "hijo")).thenReturn(mockParticipanteList);
//
//        List<Participante>  participantesList = participanteService.findAll(null, null, "hijo");
//        assertEquals(2, participantesList.size());
//        assertEquals("Alberto", participantesList.getFirst().getName());
//
//        verify(participanteRepository, times(1)).findByFilters(null, null, "hijo");
//    }

    @Test
    public void testFindById(){
        Participante selectedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.findById(selectedParticipante.getId())).thenReturn(Optional.of(selectedParticipante));

        Participante result = participanteService.findById(selectedParticipante.getId());

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
        Participante oldparticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);
        Participante wantedParticipante =  new Participante(1, "77777777U", "Lucas", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.findById(oldparticipante.getId())).thenReturn(Optional.of(oldparticipante));

        when(participanteRepository.save(oldparticipante)).thenReturn(wantedParticipante);

        mapper.map(wantedParticipante, oldparticipante);

        Participante result = participanteService.modify(oldparticipante.getId(), wantedParticipante);


        assertEquals("Lucas", result.getName());
        verify(participanteRepository).findById(oldparticipante.getId());
        verify(participanteRepository, times(1)).save(oldparticipante);
    }

    @Test
    public void testDelete(){
        Participante participante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));

        participanteService.delete(participante.getId());

        verify(participanteRepository, times(1)).delete(participante);

    }
}
