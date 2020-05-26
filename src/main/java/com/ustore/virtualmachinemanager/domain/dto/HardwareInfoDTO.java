package com.ustore.virtualmachinemanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ustore.virtualmachinemanager.domain.Disk;
import com.ustore.virtualmachinemanager.domain.Memory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HardwareInfoDTO {

    private Disk disk;
    private Memory memory;

}
