package com.svalero.asociation.config;

import com.svalero.asociation.dto.ActividadOutDto;
import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.dto.ParticipanteOutDto;
import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.model.Socio;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Socio, Long> socioToId = context ->
                context.getSource() == null ? null : context.getSource().getId();

        Converter<List<Actividad>, List<Long>> actividadesToIds = context ->
                context.getSource() == null ? List.of() :
                        context.getSource().stream().map(Actividad::getId).toList();

        modelMapper.typeMap(Participante.class, ParticipanteDto.class).addMappings(mapper -> {
            mapper.using(socioToId).map(Participante::getSocio, ParticipanteDto::setSocioID);
            mapper.using(actividadesToIds).map(Participante::getActividades, ParticipanteDto::setActividadId);
        });

        modelMapper.typeMap(Participante.class, ParticipanteOutDto.class).addMappings(mapper -> {
            mapper.using(socioToId).map(Participante::getSocio, ParticipanteOutDto::setSocioID);
            mapper.using(actividadesToIds).map(Participante::getActividades, ParticipanteOutDto::setActividadIdList);
        });

        modelMapper.typeMap(Socio.class, SocioDto.class).addMappings(mapper -> {
            mapper.map(Socio::getParticipanteList, SocioDto::setParticipanteDtoList);
        });

        modelMapper.typeMap(Actividad.class, ActividadOutDto.class).addMappings(mapper ->{
            mapper.map(Actividad::getParticipantesInscritos, ActividadOutDto::setParticipanteDtoList);
        });

        return  modelMapper;
    }
}
