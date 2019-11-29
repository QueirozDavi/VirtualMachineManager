package com.ustore.virtualmachinemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class VirtualMachine {

    @Id
    private String id;

    private Disk disk;
    private Memory memory;
    private Cpu cpu;

}


