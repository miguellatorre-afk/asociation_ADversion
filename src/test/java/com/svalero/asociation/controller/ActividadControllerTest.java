package com.svalero.asociation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.svalero.asociation.exception.ActividadNotFoundException;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.service.ActividadService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActividadController.class)
class ActividadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    public ActividadService actividadService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelMapper modelmapper;

    @Test
    void testGetAll_Return200() throws Exception {

        List<Actividad> actividadesList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null),
                new Actividad(2, "Partido de baloncesto",LocalDate.now().plusDays(20), "Grupal", 60f, true, 10, null, null)

        );

        when(actividadService.findAll(any(), any(), any())).thenReturn(actividadesList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actividades")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<Actividad> finalActividadList = thisObjectMapper.readValue(jsonResponse, new TypeReference<List<Actividad>>() {});

        assertNotNull(finalActividadList);
        assertEquals("Club de lectura", finalActividadList.getFirst().getDescription());

    }

    @Test
    void testGetAll_ByDayActivity() throws Exception {

        LocalDate filterDate = LocalDate.now();

        List<Actividad> actividadesList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now().plusDays(20), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadService.findAll(eq(filterDate), any(), any())).thenReturn(actividadesList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actividades")
                        .queryParam("dayActivity", LocalDate.now().toString())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<Actividad> actividadesListresponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertNotNull(actividadesListresponse);
        assertEquals("Partido de baloncesto", actividadesListresponse.getLast().getDescription());
    }

    @Test
    void testGetAll_ByJoin() throws Exception {

        List<Actividad> actividadesList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null),
                new Actividad(2, "Partido de baloncesto",LocalDate.now().plusDays(20), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadService.findAll(any(), any(), any())).thenReturn(actividadesList);

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actividades")
                        .queryParam("canjoin", "true")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        List<Actividad> actividadesListresponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertNotNull(actividadesListresponse);
        assertEquals(2, actividadesListresponse.size());
        assertEquals("Partido de baloncesto", actividadesListresponse.get(1).getDescription());
    }

    @Test
    void testGetAll_ByDuration() throws Exception {

        List<Actividad> actividadesList = List.of(
                new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null),
                new Actividad(1, "Partido de baloncesto",LocalDate.now().plusDays(20), "Grupal", 60f, true, 10, null, null)
        );

        when(actividadService.findAll(any(), any(), anyFloat())).thenReturn(actividadesList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actividades")
                        .queryParam("duration", "40")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<Actividad> actividadesListresponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertNotNull(actividadesListresponse);
        assertEquals(40, actividadesListresponse.getFirst().getDuration());
    }

    @Test
    void testGetById_For200() throws Exception {
        Actividad selected = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null);


        when(actividadService.findById(selected.getId())).thenReturn(selected);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/actividades/"+ selected.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Actividad actividad = thisObjectMapper.readValue(jsonResponse, Actividad.class);

        assertEquals(1, actividad.getId());
    }

    @Test
    void testGetById_For404() throws Exception {
        when(actividadService.findById(4)).thenThrow(new ActividadNotFoundException("Not found"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/actividades/4")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testAddFor201() throws Exception {

        LocalDate date = LocalDate.parse("2026-05-20");

        Actividad newActividad = new Actividad(1, "Club de lectura", date,
                "Grupal", 40f, true, 7, null, null);

        when(actividadService.add(any(Actividad.class))).thenReturn(newActividad);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());

       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(thisObjectmapper.writeValueAsString(newActividad)))
                .andExpect(status().isCreated())
               .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Actividad actividad = thisObjectmapper.readValue(jsonResponse, Actividad.class);

        assertEquals("Grupal", actividad.getTypeActivity());
    }

    @Test
    void testAddActivity_For400() throws Exception {

        Actividad newActivity = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20),
                "Grupal", 40f, true, 7, null, null);
        newActivity.setDescription(null);

        String actividadJson = objectMapper.writeValueAsString(newActivity);

        mockMvc.perform(MockMvcRequestBuilders.post("/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actividadJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testEdit_For200() throws Exception {

        Actividad originalActivity = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null);
        Actividad wantedActivity = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Individual", 40f, true, 7, null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());
        thisObjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        when(actividadService.modify(1, wantedActivity)).thenReturn(wantedActivity);

        String jsonRequest = thisObjectmapper.writeValueAsString(wantedActivity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/actividades/" + originalActivity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        Actividad responseActividad = thisObjectmapper.readValue(jsonResponse, Actividad.class);
        assertEquals(1, responseActividad.getId());
    }

    @Test
    void testEdit_For404() throws Exception {

        Actividad originalActivity = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null);
        Actividad wantedActivity = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Individual", 40f, true, 7, null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());
        thisObjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        when(actividadService.modify(1, wantedActivity)).thenThrow( new ActividadNotFoundException("Activitdad Not Found"));

        String jsonRequest = thisObjectmapper.writeValueAsString(wantedActivity);

       mockMvc.perform(MockMvcRequestBuilders.put("/actividades/" + originalActivity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_For204() throws Exception{
    Actividad selected = new Actividad(1, "Club de lectura",LocalDate.now().plusDays(20), "Grupal", 40f, true, 7, null, null);

        doNothing().when(actividadService).delete(selected.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/actividades/" + selected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}