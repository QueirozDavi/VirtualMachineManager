package com.ustore.virtualmachinemanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table(name = "MEMORY")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
