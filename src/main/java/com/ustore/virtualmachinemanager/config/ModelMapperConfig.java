package com.ustore.virtualmachinemanager.config;

import com.ustore.virtualmachinemanager.domain.MotherMachine;
import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(getMapHardwareInfoDTOtoMotherMachine(), HardwareInfoDTO.class, MotherMachine.class);
        return modelMapper;
    }

    private Converter<HardwareInfoDTO, MotherMachine> getMapHardwareInfoDTOtoMotherMachine() {
        return context -> {

            HardwareInfoDTO source = context.getSource();
            MotherMachine motherMachine = new MotherMachine();
            motherMachine.setDisk(source.getDisk());
            motherMachine.setMemory(source.getMemory());

            return motherMachine;
        };
    }



}
