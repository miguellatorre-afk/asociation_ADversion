package com.svalero.asociation.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        List<SocioDto> mockSocioDtoList = List.of(
                new SocioDto(1L, "77777777U", "Marcos", "García", "email@email.com", "888-566-323", true, LocalDate.now()),
                new SocioDto(2L, "77787777U", "Yolanda", "Del Valle", "email@email.com", "888-566-323", true, LocalDate.now().plusDays(1))
        );

        when(socioService.findAll(null, "", null)).thenReturn(mockSocioDtoList);

        //simulamos cliente Http                                   llamamos a findAll de Controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        String jsonResponse = result.getResponse().getContentAsString();
        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertNotNull(mockSocioDtoList);
        assertEquals(0, socioDtoListResponse.size());


    }

//    @Test
//    public void testFindAll_ByEntryDate() throws Exception {
//        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        List<SocioDto> mockSocioDtoList = List.of(
//                new SocioDto(1L, "77777777U", "Marcos", "García", "email@email.com", "888-566-323", true, LocalDate.now()),
//                new SocioDto(2L, "77787777U", "Yolanda", "Del Valle", "email@email.com", "888-566-323", true, LocalDate.now().plusDays(1))
//        );
//
//        when(socioService.findAll(LocalDate.now(), "", null)).thenReturn(mockSocioDtoList);
//
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
//                        .queryParam("entryDate", "2026/01/18")
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        ObjectMapper thisObjectMapper = new ObjectMapper();
//        String jsonResponse = result.getResponse().getContentAsString();
//        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});
//
//        assertEquals(LocalDate.now(), socioDtoListResponse.getLast().getEntryDate());
//
//    }


    @Test
    public void testFindAll_ByFamilyModel() throws Exception {

        List<Socio> mockSocioList = List.of(
                new Socio(1, "77777777U", "Alberto", "Gomara", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.of(2000, 5,6), null, null),
                new Socio(2, "77777327U", "Juan", "Izabal", "email@email.com", "calle2", "888-566-323", "Monoparental", true, LocalDate.of(2000, 5,6), null, null)

        );


        ModelMapper thismodelMapper = new ModelMapper();
        List<SocioDto> mockSocioDtoList = thismodelMapper.map(mockSocioList, new TypeToken<List<SocioDto>>() {}.getType());

        when(socioService.findAll(null, "Monoparental", null)).thenReturn(mockSocioDtoList);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/socios")
                        .queryParam("familyModel", "Monoparental")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper thisObjectMapper = new ObjectMapper();
        thisObjectMapper.registerModule(new JavaTimeModule());
        String jsonResponse = result.getResponse().getContentAsString();
        List<SocioDto> socioDtoListResponse = thisObjectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertEquals("Juan", socioDtoListResponse.getLast().getName());
    }


    @Test
    public void testFindAll_Return404() throws Exception {

        List<SocioDto> mockSocioDtoList = null;

        when(socioService.findAll(null, "", null)).thenReturn(mockSocioDtoList);

       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/socios/x"))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<SocioDto> socioDtoList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

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
        ModelMapper thismodelmapper = new ModelMapper();;
        SocioDto selectedDto = thismodelmapper.map(selected, SocioDto.class);
        when(socioService.findById(selectedDto.getId())).thenThrow(new SocioNotFoundException("Socio con ID" + selectedDto.getId() +" no encontrado"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/socios/2")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        SocioDto sociodto = modelmapper.map(jsonResponse, SocioDto.class);

        assertEquals(2, sociodto.getId());

    }


    @Test
    public void testAddSocio_Return201() throws Exception {
        Socio socio = new Socio(2, "77777777U", "Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);

        when(socioService.add(any(Socio.class))).thenReturn(socio);




        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/socios")
                        .accept(MediaType.APPLICATION_JSON_VALUE)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        SocioDto responsesociodto = objectMapper.readValue(jsonResponse, SocioDto.class);

        assertNotNull(responsesociodto);
    }


    @Test
    public void testAddSocio_Return400() throws Exception {
        Socio newsocio = new Socio(2, "777777U", "Marcos", "García", "email@email.com", "C Recogidas 128", "888-566-323", "Nuclear", true, LocalDate.now().plusDays(1), null, null);
//        String socioJson = objectMapper.writeValueAsString(newsocio);

        when(socioService.add(any(Socio.class))).thenThrow(new BusinessRuleException("Not accepted"));

        mockMvc.perform(MockMvcRequestBuilders.post("/socios")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(newsocio)))
//                        .content(socioJson))
                .andExpect(status().isBadRequest());

        verify(socioService, times(0)).add(any());
    }


}

