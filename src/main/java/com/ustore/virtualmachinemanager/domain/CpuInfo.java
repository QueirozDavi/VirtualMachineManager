package com.ustore.virtualmachinemanager.domain;

import lombok.Data;

@Data
public class CpuInfo {

    private String threadName;
    private String threadState;
    private String cpuTime;

}
