package com.ustore.virtualmachinemanager.service.client;

import com.ustore.virtualmachinemanager.domain.CpuInfo;
import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyHardwareInfoFallback implements MyHardwareInfoClient {

    @Override
    public HardwareInfoDTO getHardwareInformation() {
        return new HardwareInfoDTO();
    }

    @Override
    public List<CpuInfo> getCurrentCpuInfo() {
        return new ArrayList<>();
    }
}
