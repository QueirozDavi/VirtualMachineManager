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
@Table(name = "DISK")
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
