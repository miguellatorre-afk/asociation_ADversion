package com.svalero.asociation.service;

import com.svalero.asociation.model.Participante;
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
        List<Participante> mockParticipanteList = List.of(
                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
        );

        when(participanteRepository.findAll()).thenReturn(mockParticipanteList);

        List<Participante>  participanteList = participanteService.findAll(null, "", "");
        assertEquals(2, participanteList.size());
        assertEquals("Alberto", participanteList.getFirst().getName());

        verify(participanteRepository, times(1)).findAll();
        verify(participanteRepository, times(0)).findByBirthDateAfter(null);
        verify(participanteRepository, times(0)).findByNameStartingWithIgnoreCase("");
        verify(participanteRepository, times(0)).findByTypeRel("");

    }

    @Test
    public void testFindByBirthDateAfter() {
        List<Participante> mockParticipanteList = List.of(
                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
        );

        when(participanteRepository.findByBirthDateAfter(LocalDate.now())).thenReturn(mockParticipanteList);

        List<Participante>  participanteDtosList = participanteService.findAll(LocalDate.now(), "", "");

        assertEquals(2, participanteDtosList.size());
        assertEquals("Alberto", participanteDtosList.getFirst().getName());

        verify(participanteRepository, times(0)).findAll();
        verify(participanteRepository, times(1)).findByBirthDateAfter(LocalDate.now());
        verify(participanteRepository, times(0)).findByNameStartingWithIgnoreCase("");
        verify(participanteRepository, times(0)).findByTypeRel("");

    }

    @Test
    public void testfindByNameStartingWithIgnoreCase() {
        List<Participante> mockParticipanteList = List.of(
                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
        );

        when(participanteRepository.findByNameStartingWithIgnoreCase("Roberto")).thenReturn(mockParticipanteList);

        List<Participante> participanteList = participanteService.findAll(null, "Roberto", "");

        assertEquals(2, participanteList.size());
        assertEquals("Alberto", participanteList.getFirst().getName());

        verify(participanteRepository, times(0)).findAll();
        verify(participanteRepository, times(0)).findByBirthDateAfter(null);
        verify(participanteRepository, times(1)).findByNameStartingWithIgnoreCase("Roberto");
        verify(participanteRepository, times(0)).findByTypeRel("");

    }

    @Test
    public void testfindBytypeRel() {
        List<Participante> mockParticipanteList = List.of(
                new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null),
                new Participante(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null)
        );


        when(participanteRepository.findByTypeRel("hijo")).thenReturn(mockParticipanteList);

        List<Participante>  participantesList = participanteService.findAll(null, "", "hijo");
        assertEquals(2, participantesList.size());
        assertEquals("Alberto", participantesList.getFirst().getName());

        verify(participanteRepository, times(0)).findAll();
        verify(participanteRepository, times(0)).findByBirthDateAfter(null);
        verify(participanteRepository, times(0)).findByNameStartingWithIgnoreCase("");
        verify(participanteRepository, times(1)).findByTypeRel("hijo");
    }

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
