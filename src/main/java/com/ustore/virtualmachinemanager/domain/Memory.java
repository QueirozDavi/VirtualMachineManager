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

    @Column(name = "INITIAL_MEMORY")
    private double initialMemory;

    @Column(name = "USED_HEAP_MEMORY")
    private double usedHeapMemory;

    @Column(name = "MAX_HEAP_MEMORY")
    private double maxHeapMemory;

    @Column(name = "COMMITTED_MEMORY")
    private double committedMemory;

    @NotNull
    @Column(name = "TOTAL_OP_MEMORY", nullable = false)
    private double totalOpMemory;

}
