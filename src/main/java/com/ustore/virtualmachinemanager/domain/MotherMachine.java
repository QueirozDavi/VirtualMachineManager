package com.ustore.virtualmachinemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MOTHER_MACHINE")
public class MotherMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "ID_DISK")
    private Disk disk;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "ID_MEMORY")
    private Memory memory;

}


