package com.svalero.asociation.service;

import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ActividadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActividadServiceTest {
    @InjectMocks
    private ActividadService actividadService;

    @Mock
    private ActividadRepository actividadRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testFindAll() {
        List<Actividad> mockActividadList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now(), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadRepository.findAll()).thenReturn(mockActividadList);

        List<Actividad> actividadList = actividadService.findAll(null, null, null);

        assertEquals(2, actividadList.size());
        assertEquals("Club de lectura", actividadList.getFirst().getDescription());

        verify(actividadRepository, times(1)).findAll();
        verify(actividadRepository, times(0)).findByDayActivity(null);
        verify(actividadRepository, times(0)).findByCanJoin(null);
        verify(actividadRepository, times(0)).findByDuration(null);

    }

    @Test
    public void testFindByDayActivity() {
        List<Actividad> mockActividadList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now(), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadRepository.findByDayActivity(LocalDate.now())).thenReturn(mockActividadList);

        List<Actividad>  actividadList = actividadService.findAll(LocalDate.now(), null, null);

        assertEquals(2, actividadList.size());
        assertEquals("Club de lectura", actividadList.getFirst().getDescription());

        verify(actividadRepository, times(0)).findAll();
        verify(actividadRepository, times(1)).findByDayActivity(LocalDate.now());
        verify(actividadRepository, times(0)).findByCanJoin(null);
        verify(actividadRepository, times(0)).findByDuration(null);

    }

    @Test
    public void testfindByJoin() {
        List<Actividad> mockActividadList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now(), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadRepository.findByCanJoin(true)).thenReturn(mockActividadList);

        List<Actividad> actividadList = actividadService.findAll(null, true, null);

        assertEquals(2, actividadList.size());
        assertEquals("Club de lectura", actividadList.getFirst().getDescription());

        verify(actividadRepository, times(0)).findAll();
        verify(actividadRepository, times(0)).findByDayActivity(null);
        verify(actividadRepository, times(1)).findByCanJoin(true);
        verify(actividadRepository, times(0)).findByDuration(null);
    }

    @Test
    public void testfindBytypeRel() {

        List<Actividad> mockActividadList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now(), "Grupal", 60f, true, 10, null, null)
        );


        when(actividadRepository.findByDuration(40f)).thenReturn(mockActividadList);

        List<Actividad>  actividadList = actividadService.findAll(null, null, 40f);

        assertEquals(2, actividadList.size());
        assertEquals("Club de lectura", actividadList.getFirst().getDescription());

        verify(actividadRepository, times(0)).findAll();
        verify(actividadRepository, times(0)).findByDayActivity(null);
        verify(actividadRepository, times(0)).findByCanJoin(null);
        verify(actividadRepository, times(1)).findByDuration(40f);
    }

    @Test
    public void testFindById(){
        Actividad selectedActividad = new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null);

        when(actividadRepository.findById(selectedActividad.getId())).thenReturn(Optional.of(selectedActividad));

        Actividad result = actividadService.findById(selectedActividad.getId());

        assertEquals("Club de lectura", result.getDescription());
    }

    @Test
    public void testAdd(){
        Actividad newactividad = new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null);

        when(actividadRepository.save(newactividad)).thenReturn(newactividad);
        Actividad result = actividadService.add(newactividad);

        assertEquals("Club de lectura", result.getDescription());
        verify(actividadRepository, times(1)).save(newactividad);
    }

    @Test
    public void testModify(){
        Actividad oldactividad =  new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null);
        Actividad wantedActividad =  new Actividad(1, "Club de lectura",LocalDate.now().plusDays(1), "Individual", 20f, true, 10, null, null);

        when(actividadRepository.findById(oldactividad.getId())).thenReturn(Optional.of(oldactividad));

        when(actividadRepository.save(oldactividad)).thenReturn(wantedActividad);

        Actividad result = actividadService.modify(oldactividad.getId(), wantedActividad);

        mapper.map(wantedActividad, oldactividad);

        assertEquals(20f, result.getDuration());
        verify(actividadRepository).findById(oldactividad.getId());
        verify(actividadRepository, times(1)).save(oldactividad);
    }

    @Test
    public void testDelete(){
        Actividad actividad = new Actividad(1, "Club de lectura",LocalDate.now(), "Grupal", 40f, true, 7, null, null);

        when(actividadRepository.findById(actividad.getId())).thenReturn(Optional.of(actividad));

        actividadService.delete(actividad.getId());

        verify(actividadRepository, times(1)).delete(actividad);

    }
}
