package com.svalero.asociation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.svalero.asociation.exception.TrabajadorNotFoundException;
import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.service.TrabajadorService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrabajadorController.class)
class TrabajadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    public TrabajadorService trabajadorService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelMapper modelmapper;

    @Test
    public void testFindAll_Return200() throws Exception {

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(null, "", "")).thenReturn(mockTrabajadorList);

        //simulamos cliente Http                                   llamamos a findAll de Controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<Trabajador> trabajadorList = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertNotNull(trabajadorList);
        assertEquals("Hector", trabajadorList.get(0).getName());
        assertEquals(2, trabajadorList.size());
    }

    @Test
    public void testFindAll_ByEntryDate() throws Exception {

        LocalDate filterDate = LocalDate.now();

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(filterDate, "","")).thenReturn(mockTrabajadorList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores")
                        .queryParam("entryDate", LocalDate.now().toString())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();
        List<Trabajador> trabajadorListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertFalse(trabajadorListResponse.isEmpty());
        assertEquals("Hector", trabajadorListResponse.get(0).getName());
    }

    @Test
    public void testFindAll_ByName() throws Exception {

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(null, "Diana", "")).thenReturn(mockTrabajadorList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores")
                        .queryParam("name", "Diana")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        assertNotNull(jsonResponse);

        List<Trabajador> trabajadoresList = thisObjectMapper.readValue(jsonResponse,
                new TypeReference<>() {
                });

        assertEquals("Diana", trabajadoresList.get(1).getName());
    }

    @Test
    public void testFindAll_ContractType() throws Exception {

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(null, "", "Tiempo Parcial")).thenReturn(mockTrabajadorList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores")
                        .queryParam("contractType", "Tiempo Parcial")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        assertNotNull(jsonResponse);

        List<Trabajador> trabajadoresList = thisObjectMapper.readValue(jsonResponse,
                new TypeReference<>() {
                });

        assertEquals("Hector", trabajadoresList.get(0).getName());
    }

    @Test
    public void testFindAll_Return404() throws Exception {

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(isNull(), isNull(), isNull())).thenThrow(TrabajadorNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/socios"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testFindById_Return200()throws Exception{
        Trabajador selected = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null);

        when(trabajadorService.findById(1)).thenReturn(selected);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Trabajador trabajadorResult = objectMapper.readValue(jsonResponse, Trabajador.class);

        assertEquals(1, trabajadorResult.getId());

    }

    @Test
    public void testAddTrabajador_Return201() throws Exception {
        Trabajador newTrabajador = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());
        thisObjectmapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(trabajadorService.add(any(Trabajador.class))).thenReturn(newTrabajador);

        String jsonRequest = thisObjectmapper.writeValueAsString(newTrabajador);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/trabajadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        Trabajador responseTrabajador = thisObjectmapper.readValue(jsonResponse, Trabajador.class);

        assertNotNull(responseTrabajador);
        assertEquals(2, responseTrabajador.getId());
        assertEquals("Diana", responseTrabajador.getName());
    }

    @Test
    public void testAddTrabajador_Return400() throws Exception {
        Trabajador newTrabajador = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null);
        String socioJson = objectMapper.writeValueAsString(newTrabajador);

        when(trabajadorService.add(any(Trabajador.class))).thenThrow(new TrabajadorNotFoundException("Not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/trabajadores")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(socioJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyTrabajador() throws Exception {
        Trabajador selected = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null);

        doNothing().when(trabajadorService).delete(selected.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/trabajadores/" + selected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

