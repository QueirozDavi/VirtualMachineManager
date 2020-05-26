package com.ustore.virtualmachinemanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table(name = "MEMORY")
@Entity
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "INITIAL_MEMORY", nullable = false)
    private double initialMemory;

    @NotNull
    @Column(name = "USED_HEAP_MEMORY", nullable = false)
    private double usedHeapMemory;

    @NotNull
    @Column(name = "MAX_HEAP_MEMORY", nullable = false)
    private double maxHeapMemory;

    @NotNull
    @Column(name = "COMMITTED_MEMORY", nullable = false)
    private double committedMemory;

}
