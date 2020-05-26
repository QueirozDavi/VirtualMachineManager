package com.ustore.virtualmachinemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "VIRTUAL_MACHINE")
@Entity
public class VirtualMachine {

    @Id
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "ID_DISK")
    private Disk disk;

    @ManyToOne()
    @JoinColumn(name = "ID_MEMORY")
    private Memory memory;

    @Transient
    private List<CpuInfo> cpuInfos;
}



