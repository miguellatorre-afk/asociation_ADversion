package com.svalero.asociation.config;

import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.model.Participante;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Participante.class, ParticipanteDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getSocio().getId(), ParticipanteDto::setSocioID);
        });
        return  modelMapper;


    }
}
