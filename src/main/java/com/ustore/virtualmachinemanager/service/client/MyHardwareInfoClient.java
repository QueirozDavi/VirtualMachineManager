package com.ustore.virtualmachinemanager.service.client;

import com.ustore.virtualmachinemanager.config.client.ClientConfiguration;
import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "my-harware-info",
        url = "http://localhost:8081/v1/hardware-summary",
        configuration = ClientConfiguration.class,
        fallback = MyHardwareInfoFallback.class
)
public interface MyHardwareInfoClient {

    @GetMapping("/")
    HardwareInfoDTO getHardwareInformation();
}
