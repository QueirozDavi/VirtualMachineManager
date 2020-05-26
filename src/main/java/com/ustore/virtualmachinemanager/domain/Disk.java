package com.ustore.virtualmachinemanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table(name = "DISK")
@Entity
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "TOTAL_DISK_SPACE", nullable = false)
    private long totalDiskSpace;

    @NotNull
    @Column(name = "FREE_SPACE", nullable = false)
    private long freeSpace;

    @NotNull
    @Column(name = "USABLE_SPACE", nullable = false)
    private long usableSpace;


}
