package com.ustore.virtualmachinemanager.service.client;

import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class MyHardwareInfoFallback implements MyHardwareInfoClient{

    @Override
    public HardwareInfoDTO getHardwareInformation(){
        return new HardwareInfoDTO();
    }
}
