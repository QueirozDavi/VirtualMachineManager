package com.ustore.virtualmachinemanager.service;

import com.ustore.virtualmachinemanager.domain.*;
import com.ustore.virtualmachinemanager.domain.dto.VirtualMachineRequestDTO;
import com.ustore.virtualmachinemanager.exception.BadRequestException;
import com.ustore.virtualmachinemanager.exception.NotFoundException;
import com.ustore.virtualmachinemanager.repository.DiskRepository;
import com.ustore.virtualmachinemanager.repository.MemoryRepository;
import com.ustore.virtualmachinemanager.repository.MotherMachineRepository;
import com.ustore.virtualmachinemanager.repository.VirtualMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VirtualMachineService {

    private final VirtualMachineRepository repository;
    private final MotherMachineRepository motherMachineRepository;
    private final DiskRepository diskRepository;
    private final MemoryRepository memoryRepository;

    @Autowired
    public VirtualMachineService(VirtualMachineRepository repository, MotherMachineRepository motherMachineRepository,
                                 DiskRepository diskRepository, MemoryRepository memoryRepository) {
        this.repository = repository;
        this.motherMachineRepository = motherMachineRepository;
        this.diskRepository = diskRepository;
        this.memoryRepository = memoryRepository;
    }

    @Async
    public void createVirtualMachine(VirtualMachineRequestDTO virtualMachineRequestDTO) {

        MotherMachine motherMachine = motherMachineRepository.findById(virtualMachineRequestDTO.getMotherMachineId())
                .orElseThrow(() -> new NotFoundException("Mother Machine Not Found"));

        if (motherMachine.getDisk().getUsableSpace() > virtualMachineRequestDTO.getAllocatedDisk()
                && motherMachine.getMemory().getTotalOpMemory() > virtualMachineRequestDTO.getAllocatedMemory()) {

            repository.save(getNewVirtualMachine(virtualMachineRequestDTO, motherMachine));
            updateMotherMachineReferences(virtualMachineRequestDTO, motherMachine);

        } else {
            throw new BadRequestException("You don't have enough memory or disk free space.");
        }
    }

    private VirtualMachine getNewVirtualMachine(VirtualMachineRequestDTO virtualMachineRequestDTO,
                                                MotherMachine motherMachine) {
        Disk disk;
        Memory memory;
        VirtualMachine virtualMachine;
        disk = new Disk();
        disk.setUsableSpace(virtualMachineRequestDTO.getAllocatedDisk());
        disk.setFreeSpace(virtualMachineRequestDTO.getAllocatedDisk());
        disk.setTotalDiskSpace(virtualMachineRequestDTO.getAllocatedDisk());

        memory = new Memory();
        memory.setTotalOpMemory(virtualMachineRequestDTO.getAllocatedMemory());
        memory.setCommittedMemory(motherMachine.getMemory().getCommittedMemory());
        memory.setInitialMemory(motherMachine.getMemory().getInitialMemory());
        memory.setMaxHeapMemory(motherMachine.getMemory().getMaxHeapMemory());
        memory.setUsedHeapMemory(motherMachine.getMemory().getUsedHeapMemory());

        virtualMachine = new VirtualMachine();
        virtualMachine.setDisk(diskRepository.save(disk));
        virtualMachine.setMemory(memoryRepository.save(memory));
        virtualMachine.setStatus(StatusEnum.ACTIVE);
        return virtualMachine;
    }

    private void updateMotherMachineReferences(VirtualMachineRequestDTO virtualMachineRequestDTO,
                                               MotherMachine motherMachine) {
        motherMachine.getDisk().setUsableSpace(motherMachine.getDisk().getUsableSpace()
                - virtualMachineRequestDTO.getAllocatedDisk());

        motherMachine.getMemory().setTotalOpMemory(motherMachine.getMemory().getTotalOpMemory()
                - virtualMachineRequestDTO.getAllocatedMemory());

        motherMachine.getDisk().setFreeSpace(motherMachine.getDisk().getFreeSpace()
                - virtualMachineRequestDTO.getAllocatedDisk());

        diskRepository.save(motherMachine.getDisk());
        memoryRepository.save(motherMachine.getMemory());
    }
}
