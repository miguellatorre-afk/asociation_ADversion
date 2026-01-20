package com.svalero.asociation.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import static org.mockito.ArgumentMatchers.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.SocioNotFoundException;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.service.SocioService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SocioController.class)
public class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    public SocioService socioService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelMapper modelmapper;

    @Test
    public void testFindAll_Return200() throws Exception {

        List<Socio> mockSocioList = List.of(
                new Socio(1, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.of(2000, 5,6), null, null),
                new Socio(2, "77777327U", "Juan", "Izabal", "email@email.com", "calle2", "888-566-323", "Monoparental", true, LocalDate.of(2000, 5,6), null, null)
        );

        ModelMapper thismodelMapper = new ModelMapper();
        List<SocioDto> mockSocioDtoList = thismodelMapper.map(mockSocioList, new TypeToken<List<SocioDto>>() {}.getType());

        when(socioService.findAll(isNull(), isNull(), isNull())).thenReturn(mockSocioDtoList);

        //simulamos cliente Http                                   llamamos a findAll de Controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<List<SocioDto>>() {});

        assertNotNull(mockSocioDtoList);
        assertEquals("Alberto", socioDtoListResponse.get(0).getName());
        assertEquals(2, socioDtoListResponse.size());
    }

    @Test
    public void testFindAll_ByEntryDate() throws Exception {

        LocalDate filterDate = LocalDate.of(2000, 5, 6);

        List<Socio> mockSocioList = List.of(
                new Socio(1, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.of(2000, 5,6), null, null),
                new Socio(2, "77777327U", "Juan", "Izabal", "email@email.com", "calle2", "888-566-323", "Monoparental", true, LocalDate.of(2000, 5,6), null, null)
        );

        ModelMapper thismodelMapper = new ModelMapper();
        List<SocioDto> mockSocioDtoList = thismodelMapper.map(mockSocioList, new TypeToken<List<SocioDto>>() {}.getType());

        when(socioService.findAll(eq(filterDate), isNull(), isNull())).thenReturn(mockSocioDtoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                        .queryParam("entryDate", "2000-05-06")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();
        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<List<SocioDto>>() {});

        assertFalse(socioDtoListResponse.isEmpty(), "La lista sigue vacía, revisa el mapeo del Controlador");
        assertEquals("Alberto", socioDtoListResponse.get(0).getName());
    }


    @Test
    public void testFindAll_ByFamilyModel() throws Exception {

        List<Socio> mockSocioList = List.of(
                new Socio(1, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.of(2000, 5,6), null, null),
                new Socio(2, "77777327U", "Juan", "Izabal", "email@email.com", "calle2", "888-566-323", "Monoparental", true, LocalDate.of(2000, 5,6), null, null)
        );

        ModelMapper thismodelMapper = new ModelMapper();
        List<SocioDto> mockSocioDtoList = thismodelMapper.map(mockSocioList, new TypeToken<List<SocioDto>>() {}.getType());


        when(socioService.findAll(null, "Monoparental",null)).thenReturn(mockSocioDtoList);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                        .queryParam("familyModel", "Monoparental")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();


        assertNotNull(jsonResponse);
        assertFalse(jsonResponse.isEmpty(), "La respuesta JSON llegó vacía");

        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse,
                new TypeReference<List<SocioDto>>() {});

        assertEquals("Juan", socioDtoListResponse.get(1).getName());
    }

    @Test
    public void testFindAll_ByActive() throws Exception {

        List<Socio> mockSocioList = List.of(
                new Socio(1, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.of(2000, 5,6), null, null),
                new Socio(2, "77777327U", "Juan", "Izabal", "email@email.com", "calle2", "888-566-323", "Monoparental", true, LocalDate.of(2000, 5,6), null, null)

        );

        ModelMapper thismodelMapper = new ModelMapper();

        List<SocioDto> mockSocioDtoList = thismodelMapper.map(mockSocioList, new TypeToken<List<SocioDto>>() {}.getType());

        when(socioService.findAll(any(), any(), any())).thenReturn(mockSocioDtoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                        .queryParam("active", "true")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonResponse = result.getResponse().getContentAsString();

        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertNotNull(socioDtoListResponse);
        assertEquals("Alberto", socioDtoListResponse.get(0).getName());
    }


    @Test
    public void testFindAll_Return404() throws Exception {

        List<SocioDto> mockSocioDtoList = List.of(
                new SocioDto(1L, "77777777U", "Marcos", "García", "email@email.com", "888-566-323", true, LocalDate.now()),
                new SocioDto(2L, "77787777U", "Yolanda", "Del Valle", "email@email.com", "888-566-323", true, LocalDate.now().plusDays(1))
        );

        when(socioService.findAll(null, "", null)).thenThrow(SocioNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/socios"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testFindById_Return200()throws Exception{
        Socio selected = new Socio(2, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);
        ModelMapper thismodelmapper = new ModelMapper();
        SocioDto selectedDto = thismodelmapper.map(selected, SocioDto.class);

        when(socioService.findById(selectedDto.getId())).thenReturn(selectedDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/socios/"+ selectedDto.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        SocioDto sociodto = objectMapper.readValue(jsonResponse, SocioDto.class);

        assertEquals(2, sociodto.getId());

    }

    @Test
    public void testFindById_Return404()throws Exception{
        Socio selected = new Socio(1, "77777777U", "Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);

        when(socioService.findById(selected.getId())).thenThrow(new SocioNotFoundException("Socio con ID" + selected.getId() +" no encontrado"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/socios/"+ selected.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @Test
    public void testAddSocio_Return201() throws Exception {

        Socio newsocio = new Socio(2, "77777777U", "Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);
        when(socioService.add(any(Socio.class))).thenReturn(newsocio);

        ObjectMapper thisObjectmapper = new ObjectMapper();

        thisObjectmapper.registerModule(new JavaTimeModule());
        thisObjectmapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        thisObjectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonRequest = thisObjectmapper.writeValueAsString(newsocio);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        SocioDto responsesociodto = thisObjectmapper.readValue(jsonResponse, SocioDto.class);

        assertNotNull(responsesociodto);
        assertEquals(2, responsesociodto.getId());
        assertEquals("Marcos", responsesociodto.getName());
    }


    @Test
    public void testAddSocio_Return400() throws Exception {
        Socio newsocio = new Socio(2, "777777U", "Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);
        String socioJson = objectMapper.writeValueAsString(newsocio);

        when(socioService.add(any(Socio.class))).thenThrow(new BusinessRuleException("Not accepted"));

        mockMvc.perform(MockMvcRequestBuilders.post("/socios")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(socioJson))
                .andExpect(status().isBadRequest());

    }


}

