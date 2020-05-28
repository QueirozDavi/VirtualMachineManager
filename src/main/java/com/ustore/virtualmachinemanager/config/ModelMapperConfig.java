package com.ustore.virtualmachinemanager.config;

import com.ustore.virtualmachinemanager.domain.Disk;
import com.ustore.virtualmachinemanager.domain.MotherMachine;
import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private static final long CONVERT_NUMBER = 1073741824;

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
            motherMachine.setDisk(setDisk(source.getDisk()));
            motherMachine.setMemory(source.getMemory());

            return motherMachine;
        };
    }

    private Disk setDisk(Disk disk) {

        disk.setFreeSpace(disk.getFreeSpace() / CONVERT_NUMBER);
        disk.setTotalDiskSpace(disk.getTotalDiskSpace() / CONVERT_NUMBER);
        disk.setUsableSpace(disk.getUsableSpace() / CONVERT_NUMBER);
        return disk;
    }


}
