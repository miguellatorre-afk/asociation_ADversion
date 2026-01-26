package com.svalero.asociation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.svalero.asociation.exception.BusinessRuleException;
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

        when(trabajadorService.findAll(null, null, null)).thenReturn(mockTrabajadorList);

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

        when(trabajadorService.findAll(filterDate, null,null)).thenReturn(mockTrabajadorList);

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


        assertEquals("Hector", trabajadorListResponse.get(0).getName());
    }

    @Test
    public void testFindAll_ByName() throws Exception {

        List<Trabajador> mockTrabajadorList = List.of(
                new Trabajador(1, "77777777U", "Hector", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Parcial", null, null),
                new Trabajador(2, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null)
        );

        when(trabajadorService.findAll(null, "Diana", null)).thenReturn(mockTrabajadorList);

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

        when(trabajadorService.findAll(null, null, "Tiempo Parcial")).thenReturn(mockTrabajadorList);

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

        when(trabajadorService.findAll(any(), any(), any()))
                .thenThrow(new TrabajadorNotFoundException("No trabajadores"));

        mockMvc.perform(MockMvcRequestBuilders.get("/trabajadores"))
                .andExpect(status().isNotFound());
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
        Trabajador newTrabajador = new Trabajador(0, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now().minusDays(1), LocalDate.now(), "Tiempo Completo", null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());

        when(trabajadorService.add(newTrabajador)).thenReturn(newTrabajador);

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
        assertEquals(0, responseTrabajador.getId());
        assertEquals("Diana", responseTrabajador.getName());
    }

    @Test
    public void testAddTrabajador_Return400() throws Exception {

        Trabajador newTrabajador = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Contrato Temporal", null, null);

        newTrabajador.setName(null);

        String jsonRequest = objectMapper.writeValueAsString(newTrabajador);

        mockMvc.perform(MockMvcRequestBuilders.post("/trabajadores")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEditTrabajador_For200() throws Exception {
        Trabajador wantedTrabajador = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.of(1995, 5, 10), LocalDate.now(), "Contrato Temporal", null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());

        when(trabajadorService.modify(eq(1L), any(Trabajador.class))).thenReturn(wantedTrabajador);

        String jsonRequest = thisObjectmapper.writeValueAsString(wantedTrabajador);

        mockMvc.perform(MockMvcRequestBuilders.put("/trabajadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());


        Trabajador responseActividad = thisObjectmapper.readValue(jsonRequest, Trabajador.class);
        assertEquals(1, responseActividad.getId());
    }

    @Test
    void testEditTrabajador_For404() throws Exception {
        Trabajador wantedTrabajador = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email.com", "888-566-323", LocalDate.of(1995, 5, 10), LocalDate.now(), "Contrato Temporal", null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());

        when(trabajadorService.modify(anyLong(), any(Trabajador.class)))
                .thenThrow(new TrabajadorNotFoundException("Trabajador Not Found"));

        String jsonRequest = thisObjectmapper.writeValueAsString(wantedTrabajador);

        mockMvc.perform(MockMvcRequestBuilders.put("/trabajadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteTrabajador_For204() throws Exception{
        Trabajador selected = new Trabajador(1, "11177777P", "Diana", "Aladia", "email@email", "888-566-323", LocalDate.now(), LocalDate.now(), "Tiempo Completo", null, null);

        doNothing().when(trabajadorService).delete(selected.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/trabajadores/" + selected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

