package com.ustore.virtualmachinemanager.service;

import com.ustore.virtualmachinemanager.domain.*;
import com.ustore.virtualmachinemanager.domain.dto.VirtualMachineRequestDTO;
import com.ustore.virtualmachinemanager.exception.BadRequestException;
import com.ustore.virtualmachinemanager.exception.NotFoundException;
import com.ustore.virtualmachinemanager.repository.DiskRepository;
import com.ustore.virtualmachinemanager.repository.MemoryRepository;
import com.ustore.virtualmachinemanager.repository.MotherMachineRepository;
import com.ustore.virtualmachinemanager.repository.VirtualMachineRepository;
import com.ustore.virtualmachinemanager.service.client.MyHardwareInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VirtualMachineService {

    private final VirtualMachineRepository repository;
    private final MotherMachineRepository motherMachineRepository;
    private final DiskRepository diskRepository;
    private final MemoryRepository memoryRepository;
    private final MotherMachineService motherMachineService;
    private final MyHardwareInfoClient myHardwareInfoClient;

    private static final String NOT_ENOUGH_SPACE = "You don't have enough memory or disk free space.";

    @Autowired
    public VirtualMachineService(VirtualMachineRepository repository, MotherMachineRepository motherMachineRepository,
                                 DiskRepository diskRepository, MemoryRepository memoryRepository,
                                 MotherMachineService motherMachineService, MyHardwareInfoClient myHardwareInfoClient) {
        this.repository = repository;
        this.motherMachineRepository = motherMachineRepository;
        this.diskRepository = diskRepository;
        this.memoryRepository = memoryRepository;
        this.motherMachineService = motherMachineService;
        this.myHardwareInfoClient = myHardwareInfoClient;
    }


    public VirtualMachine getVirtualMachineResources(Long id) {
        VirtualMachine virtualMachine = repository.findById(id).orElseThrow(
                () -> new BadRequestException("Virtual Machine with id: " + id + " not found."));

        virtualMachine.setCpuInfos(myHardwareInfoClient.getCurrentCpuInfo());

        return virtualMachine;
    }

    @Async
    public void createVirtualMachine(VirtualMachineRequestDTO virtualMachineRequestDTO) {

        MotherMachine motherMachine = motherMachineRepository.findById(virtualMachineRequestDTO.getMotherMachineId())
                .orElseThrow(() -> new NotFoundException("Mother Machine Not Found"));

        if (motherMachine.getDisk().getUsableSpace() > virtualMachineRequestDTO.getAllocatedDisk()
                && motherMachine.getMemory().getTotalOpMemory() > virtualMachineRequestDTO.getAllocatedMemory()) {

            repository.save(getNewVirtualMachine(virtualMachineRequestDTO, motherMachine));
            motherMachineService.updateMotherMachineReferences(virtualMachineRequestDTO, motherMachine);

        } else {
            throw new BadRequestException(NOT_ENOUGH_SPACE);
        }
    }

    public void deleteVirtualMachine(Long id){
        VirtualMachine virtualMachine = repository.findById(id).orElseThrow(
                () -> new BadRequestException("Virtual Machine with id: " + id + " not found."));

        MotherMachine motherMachine = virtualMachine.getMotherMachine();
        motherMachine.getDisk().setFreeSpace(motherMachine.getDisk().getFreeSpace() +
                virtualMachine.getDisk().getTotalDiskSpace());
        motherMachine.getDisk().setUsableSpace(motherMachine.getDisk().getUsableSpace() +
                Math.round(virtualMachine.getDisk().getTotalDiskSpace()));

        motherMachineRepository.save(motherMachine);
        repository.delete(virtualMachine);

    }

    public VirtualMachine updateVirtualMachine(Long id, VirtualMachineRequestDTO virtualMachineRequestDTO) {
        VirtualMachine virtualMachine = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Virtual Machine with id: " + id + " not found."));

        verifyMotherMachineMemoryAvailability(virtualMachineRequestDTO, virtualMachine);
        verifyMotherMachineDiskAvailability(virtualMachineRequestDTO, virtualMachine);
        updateMotherMachineDiskAndMemoryReferences(virtualMachineRequestDTO, virtualMachine);

        virtualMachine.getDisk().setTotalDiskSpace(virtualMachineRequestDTO.getAllocatedDisk());
        virtualMachine.getDisk().setUsableSpace(virtualMachineRequestDTO.getAllocatedDisk());
        virtualMachine.getDisk().setFreeSpace(virtualMachineRequestDTO.getAllocatedDisk());
        virtualMachine.getMemory().setTotalOpMemory(virtualMachineRequestDTO.getAllocatedMemory());

        return repository.save(virtualMachine);

    }

    private void updateMotherMachineDiskAndMemoryReferences(VirtualMachineRequestDTO virtualMachineRequestDTO, VirtualMachine virtualMachine) {
        long diskDiff;
        boolean isDiskMinor = false;
        double memoryDiff;
        boolean isMemoryMinor = false;

        if (virtualMachine.getDisk().getUsableSpace() > virtualMachineRequestDTO.getAllocatedDisk()) {
            diskDiff = virtualMachine.getDisk().getUsableSpace() - virtualMachineRequestDTO.getAllocatedDisk();

        } else {
            isDiskMinor = true;
            diskDiff = virtualMachineRequestDTO.getAllocatedDisk() - virtualMachine.getDisk().getUsableSpace();
        }

        if (virtualMachine.getMemory().getTotalOpMemory() > virtualMachineRequestDTO.getAllocatedMemory()) {
            memoryDiff = virtualMachine.getMemory().getTotalOpMemory() - virtualMachineRequestDTO.getAllocatedMemory();
        } else {
            isMemoryMinor = true;
            memoryDiff = virtualMachineRequestDTO.getAllocatedMemory() - virtualMachine.getMemory().getTotalOpMemory();
        }

        updateMotherMachineDiskReferences(virtualMachine, diskDiff, isDiskMinor);
        updateMotherMachineMemoryReferences(virtualMachine, memoryDiff, isMemoryMinor);
    }

    private void verifyMotherMachineDiskAvailability(VirtualMachineRequestDTO virtualMachineRequestDTO, VirtualMachine virtualMachine) {

        if (virtualMachine.getMotherMachine().getDisk().getUsableSpace() < virtualMachineRequestDTO.getAllocatedDisk() ||
                virtualMachine.getMotherMachine().getMemory().getTotalOpMemory() < virtualMachineRequestDTO.getAllocatedMemory())
            throw new BadRequestException(NOT_ENOUGH_SPACE);

    }

    private void verifyMotherMachineMemoryAvailability(VirtualMachineRequestDTO virtualMachineRequestDTO, VirtualMachine virtualMachine) {

        if (virtualMachine.getMotherMachine().getDisk().getUsableSpace() < virtualMachineRequestDTO.getAllocatedDisk() ||
                virtualMachine.getMotherMachine().getMemory().getTotalOpMemory() < virtualMachineRequestDTO.getAllocatedMemory())
            throw new BadRequestException(NOT_ENOUGH_SPACE);

    }

    private void updateMotherMachineDiskReferences(VirtualMachine virtualMachine, long diskDiff, boolean isDiskMinor) {

        if (isDiskMinor) {
            virtualMachine.getMotherMachine().getDisk().setUsableSpace(virtualMachine.getMotherMachine().getDisk().getUsableSpace() - diskDiff);
            virtualMachine.getMotherMachine().getDisk().setFreeSpace(virtualMachine.getMotherMachine().getDisk().getFreeSpace() - diskDiff);

        } else {
            virtualMachine.getMotherMachine().getDisk().setUsableSpace(virtualMachine.getMotherMachine().getDisk().getUsableSpace() + diskDiff);
            virtualMachine.getMotherMachine().getDisk().setFreeSpace(virtualMachine.getMotherMachine().getDisk().getFreeSpace() + diskDiff);
        }

        motherMachineRepository.save(virtualMachine.getMotherMachine());
    }

    private void updateMotherMachineMemoryReferences(VirtualMachine virtualMachine, double memoryDiff,
                                                     boolean isMememoryMinor) {

        if (isMememoryMinor)
            virtualMachine.getMotherMachine().getMemory().setTotalOpMemory(virtualMachine.getMotherMachine().getMemory()
                    .getTotalOpMemory() - memoryDiff);
        else
            virtualMachine.getMotherMachine().getMemory().setTotalOpMemory(virtualMachine.getMotherMachine().getMemory()
                    .getTotalOpMemory() + memoryDiff);

        motherMachineRepository.save(virtualMachine.getMotherMachine());
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


}
