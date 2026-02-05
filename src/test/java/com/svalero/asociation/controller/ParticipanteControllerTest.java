package com.svalero.asociation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.ParticipanteNotFoundException;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.service.ParticipanteService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    public ParticipanteService participanteService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelMapper modelmapper;

    @Test
    void getAll() throws Exception {

        ParticipanteDto dto1 = new ParticipanteDto();
        dto1.setId(1L);
        dto1.setDni("77777777U");
        dto1.setName("Alberto");
        dto1.setSurname("Gomara");
        dto1.setEmail("email@email.com");
        dto1.setPhoneNumber("888-566-323");
        dto1.setTypeRel("hijo");
        dto1.setSocioID(1L);

        ParticipanteDto dto2 = new ParticipanteDto();
        dto2.setId(2L); dto2.setDni("77777327U"); dto2.setName("Roberto"); dto2.setSurname("Izabal"); dto2.setEmail("email@email.com"); dto2.setPhoneNumber("888-566-323");
        dto2.setTypeRel("hijo"); dto2.setSocioID(1L);

        when(participanteService.findAll(any(), any(), any()))
                .thenReturn(List.of(dto1, dto2));

        mockMvc.perform(MockMvcRequestBuilders.get("/participantes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alberto"))
                .andExpect(jsonPath("$[0].socioID").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Roberto"))
                .andExpect(jsonPath("$[1].socioID").value(1));
    }

    @Test
    public void testFindAllParticipante_ByBirthDate() throws Exception {

        LocalDate date = LocalDate.now().minusYears(30);

        List<ParticipanteDto> mockParticipanteDtoList = List.of(
                new ParticipanteDto(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323",LocalDate.now(), "ninguna", "hijo", 1L),
                new ParticipanteDto(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(), "ninguna", "hijo", 1L)
        );


        when(participanteService.findAll(date, null, null)).thenReturn(mockParticipanteDtoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/participantes")
                        .queryParam("birthDate", LocalDate.now().minusYears(30).toString())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();
        List<Participante> participanteListResult = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertEquals("Alberto", participanteListResult.get(0).getName());
    }


    @Test
    public void testFindAllParticipante_ByName() throws Exception {

        List<ParticipanteDto> mockParticipanteDtoList = List.of(
                new ParticipanteDto(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323",LocalDate.now(), "ninguna", "hijo", 1L),
                new ParticipanteDto(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(), "ninguna", "hijo", 1L)
        );

        when(participanteService.findAll(null, "Roberto",null)).thenReturn(mockParticipanteDtoList);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/participantes")
                        .queryParam("name", "Roberto")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        assertNotNull(jsonResponse);

        List<Participante> participanteListResult = thisObjectMapper.readValue(jsonResponse,
                new TypeReference<>() {
                });

        assertEquals("Roberto", participanteListResult.get(1).getName());
    }

    @Test
    public void testFindAllParticipante_ByTypeRel() throws Exception {

        List<ParticipanteDto> mockParticipanteDtoList = List.of(
                new ParticipanteDto(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323",LocalDate.now(), "ninguna", "hijo", 1L),
                new ParticipanteDto(2, "77777327U", "Roberto", "Izabal", "email@email.com", "888-566-323", LocalDate.now(), "ninguna", "hijo", 1L)
        );

        when(participanteService.findAll(null, null, "hijo")).thenReturn(mockParticipanteDtoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/participantes")
                        .queryParam("typeRel", "hijo")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<Participante> participanteListResult = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertNotNull(participanteListResult);
        assertEquals("Alberto", participanteListResult.get(0).getName());
    }

    @Test
    public void testFindParticipanteById_Return200()throws Exception{
        Participante selectedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteService.findById(selectedParticipante.getId())).thenReturn(selectedParticipante);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/participantes/"+ selectedParticipante.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Participante participante = objectMapper.readValue(jsonResponse, Participante.class);

        assertEquals(1, participante.getId());

    }

    @Test
    public void testFindParticipanteById_Return404()throws Exception{
        Participante selectedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteService.findById(selectedParticipante.getId())).thenThrow(new ParticipanteNotFoundException("Participante con ID" + selectedParticipante.getId() +" no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/participante/"+ selectedParticipante.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddParticipante_Return201() throws Exception {

        Participante participante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());

        when(participanteService.add(participante)).thenReturn(participante);

        String jsonRequest = thisObjectmapper.writeValueAsString(participante);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        Participante responseParticipante = thisObjectmapper.readValue(jsonResponse, Participante.class);

        assertNotNull(responseParticipante);
        assertEquals(1, responseParticipante.getId());
        assertEquals("Alberto", responseParticipante.getName());
    }

    @Test
    public void testAddParticipante_Return400() throws Exception {

        Participante newparticipante =  new Participante(1, "777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", null, null, null);

        newparticipante.setDni(null);

        String jsonRequest = objectMapper.writeValueAsString(newparticipante);

        mockMvc.perform(MockMvcRequestBuilders.post("/participantes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddParticipanteDto_Return200() throws Exception {
        Socio socio = new Socio();
        socio.setId(1);

        Participante newparticipante =  new Participante(1, "777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now(),LocalDate.now(), "ninguna", "hijo", socio, null, null);
        ParticipanteDto participanteDto = new ParticipanteDto();
        participanteDto.setSocioID(1L);

        ObjectMapper thisobjectmapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

        when(participanteService.addDto(any(ParticipanteDto.class), eq(1L)))
                .thenReturn(newparticipante);
        when(modelmapper.map(eq(newparticipante), eq(ParticipanteDto.class)))
                .thenReturn(participanteDto);

        String jsonRequest = thisobjectmapper.writeValueAsString(participanteDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/socios/1/participante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void testEditParticipante_For200() throws Exception {

        Participante originalParticipante = new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);
        Participante wantedParticipante = new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "padre", null, null, null);

        ObjectMapper thisobjectmapper = new ObjectMapper();
        thisobjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        thisobjectmapper.registerModule(new JavaTimeModule());

        when(participanteService.modify(1, wantedParticipante)).thenReturn(wantedParticipante);

        String jsonRequest = thisobjectmapper.writeValueAsString(wantedParticipante);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/participantes/" + originalParticipante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        Participante responseParticipante = thisobjectmapper.readValue(jsonResponse, Participante.class);
        assertEquals(1, responseParticipante.getId());
        assertEquals("padre", responseParticipante.getTypeRel());
    }



    @Test
    void testEdiParticipante_For200() throws Exception {

        Participante originalParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);
        Participante wantedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "padre", null, null, null);

        ObjectMapper thisObjectmapper = new ObjectMapper();
        thisObjectmapper.registerModule(new JavaTimeModule());
        thisObjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        when(participanteService.modify(1, wantedParticipante)).thenReturn(wantedParticipante);

        String jsonRequest = thisObjectmapper.writeValueAsString(wantedParticipante);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/participantes/" + originalParticipante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        Participante responseActividad = thisObjectmapper.readValue(jsonResponse, Participante.class);
        assertEquals(1, responseActividad.getId());
        assertEquals("padre", responseActividad.getTypeRel());
    }

    @Test
    void testEditParticipante_For404() throws Exception {

        Participante originalParticipante = new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);
        Participante wantedParticipante = new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        ObjectMapper thisobjectmapper = new ObjectMapper();
        thisobjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        thisobjectmapper.registerModule(new JavaTimeModule());

        when(participanteService.modify(1, wantedParticipante)).thenThrow(new ParticipanteNotFoundException("Participante Not Found"));

        String jsonRequest = thisobjectmapper.writeValueAsString(wantedParticipante);

        mockMvc.perform(MockMvcRequestBuilders.put("/participantes/" + originalParticipante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_For204() throws Exception{
        Participante selected =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        doNothing().when(participanteService).delete(selected.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/participantes/" + selected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDelete_For404()throws Exception{
        Participante selectedParticipante =  new Participante(1, "77777777U", "Alberto", "Gomara", "email@email.com", "888-566-323", LocalDate.now().minusYears(20),LocalDate.now(), "ninguna", "hijo", null, null, null);

        when(participanteService.findById(selectedParticipante.getId())).thenThrow(new ParticipanteNotFoundException("Participante con ID" + selectedParticipante.getId() +" no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/participante/"+ selectedParticipante.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}