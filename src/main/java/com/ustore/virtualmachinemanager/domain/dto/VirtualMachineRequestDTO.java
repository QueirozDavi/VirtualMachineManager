package com.ustore.virtualmachinemanager.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class VirtualMachineRequestDTO {

    @NotNull
    private long allocatedDisk;
    @NotNull
    private double allocatedMemory;
    @NotNull
    private Long motherMachineId;


}
