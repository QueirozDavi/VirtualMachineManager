package com.ustore.virtualmachinemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "MOTHER_MACHINE")
public class MotherMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "DISK", nullable = false)
    private Disk disk;

    @NotNull
    @Column(name = "MEMORY", nullable = false)
    private Memory memory;

}


