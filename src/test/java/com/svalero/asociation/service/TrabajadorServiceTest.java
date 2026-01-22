package com.svalero.asociation.service;

import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.repository.TrabajadorRepository;
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
public class TrabajadorServiceTest{

    @InjectMocks
    private TrabajadorService trabajadorService;

    @Mock
    private TrabajadorRepository trabajadorRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void findAll() {
        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );
        when(trabajadorRepository.findAll()).thenReturn(mockTrabajadorList);

        List<Trabajador> trabajadorList = trabajadorService.findAll(null, null, null);

        assertEquals(2, trabajadorList.size());
        assertEquals("Diana", trabajadorList.getLast().getName());

        verify(trabajadorRepository, times(1)).findAll();
        verify(trabajadorRepository, times(0)).findByEntryDateAfter(null);
        verify(trabajadorRepository, times(0)).findByNameStartingWithIgnoreCase(null);
        verify(trabajadorRepository, times(0)).findByContractType(null);
    }

    @Test
    void findAllByEntryDateAfter() {
        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now().plusDays(1), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );
        when(trabajadorRepository.findByEntryDateAfter(LocalDate.now())).thenReturn(mockTrabajadorList);

        List<Trabajador> trabajadorList = trabajadorService.findAll(LocalDate.now(), null, null);

        assertEquals(2, trabajadorList.size());
        assertEquals("Diana", trabajadorList.getLast().getName());

        verify(trabajadorRepository, times(0)).findAll();
        verify(trabajadorRepository, times(1)).findByEntryDateAfter(LocalDate.now());
        verify(trabajadorRepository, times(0)).findByNameStartingWithIgnoreCase(null);
        verify(trabajadorRepository, times(0)).findByContractType(null);

    }

    @Test
    void findByNameStartingWithIgnoreCase(){
        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );
        when(trabajadorRepository.findByNameStartingWithIgnoreCase("Diana")).thenReturn(mockTrabajadorList);

        List<Trabajador> trabajadorList = trabajadorService.findAll(null, "Diana", "");

        assertEquals(2, trabajadorList.size());
        assertEquals("Hector", trabajadorList.getFirst().getName());

        verify(trabajadorRepository, times(0)).findAll();
        verify(trabajadorRepository, times(0)).findByEntryDateAfter(null);
        verify(trabajadorRepository, times(1)).findByNameStartingWithIgnoreCase("Diana");
        verify(trabajadorRepository, times(0)).findByContractType(null);
    }

    @Test
    void findByContractType(){
        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );
        when(trabajadorRepository.findByContractType("Tiempo Parcial")).thenReturn(mockTrabajadorList);

        List<Trabajador> trabajadorList = trabajadorService.findAll(null, null, "Tiempo Parcial");

        assertEquals(2, trabajadorList.size());
        assertEquals("Hector", trabajadorList.getFirst().getName());

        verify(trabajadorRepository, times(0)).findAll();
        verify(trabajadorRepository, times(0)).findByEntryDateAfter(LocalDate.now());
        verify(trabajadorRepository, times(0)).findByNameStartingWithIgnoreCase("null");
        verify(trabajadorRepository, times(1)).findByContractType("Tiempo Parcial");
    }

    @Test
    void findById() {
        Trabajador selectedTrabajador =   new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null);

        when(trabajadorRepository.findById(selectedTrabajador.getId())).thenReturn(Optional.of(selectedTrabajador));

        Trabajador result = trabajadorService.findById(selectedTrabajador.getId());

        assertEquals("Hector", result.getName());

    }

    @Test
    void testAdd() {
        Trabajador newTrabajador =   new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null);

        when(trabajadorRepository.save(newTrabajador)).thenReturn(newTrabajador);
        Trabajador result = trabajadorService.add(newTrabajador);

        assertEquals("Hector", result.getName());
        verify(trabajadorRepository, times(1)).save(newTrabajador);
    }

    @Test
    void testModify() {

        Trabajador oldTrabajador =   new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null);
        Trabajador wantedTrabajador =   new Trabajador(1, "1112777K", "Gustavo", "Aladia", "email@email", "982-966-710", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null);

        when(trabajadorRepository.findById(oldTrabajador.getId())).thenReturn(Optional.of(oldTrabajador));

        when(trabajadorRepository.save(oldTrabajador)).thenReturn(wantedTrabajador);

        mapper.map(wantedTrabajador, oldTrabajador);

        Trabajador result = trabajadorService.modify(oldTrabajador.getId(), wantedTrabajador);


        assertEquals("Gustavo", result.getName());
        verify(trabajadorRepository).findById(oldTrabajador.getId());
        verify(trabajadorRepository, times(1)).save(oldTrabajador);
    }

    @Test
    void testDelete() {
        Trabajador trabajador =   new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null);

        when(trabajadorRepository.findById(trabajador.getId())).thenReturn(Optional.of(trabajador));

        trabajadorService.delete(trabajador.getId());

        verify(trabajadorRepository, times(1)).delete(trabajador);
    }

}