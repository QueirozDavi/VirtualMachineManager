package com.ustore.virtualmachinemanager.service;

import com.ustore.virtualmachinemanager.domain.Disk;
import com.ustore.virtualmachinemanager.domain.Memory;
import com.ustore.virtualmachinemanager.domain.MotherMachine;
import com.ustore.virtualmachinemanager.exception.BadRequestException;
import com.ustore.virtualmachinemanager.exception.NotFoundException;
import com.ustore.virtualmachinemanager.repository.DiskRepository;
import com.ustore.virtualmachinemanager.repository.MemoryRepository;
import com.ustore.virtualmachinemanager.repository.MotherMachineRepository;
import com.ustore.virtualmachinemanager.service.client.MyHardwareInfoClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherMachineService {

    private final MyHardwareInfoClient myHardwareInfoClient;
    private final ModelMapper modelMapper;
    private final MotherMachineRepository repository;
    private final DiskRepository diskRepository;
    private final MemoryRepository memoryRepository;

    @Autowired
    public MotherMachineService(MyHardwareInfoClient myHardwareInfoClient, ModelMapper modelMapper,
                                MotherMachineRepository motherMachineRepository, DiskRepository diskRepository,
                                MemoryRepository memoryRepository) {
        this.myHardwareInfoClient = myHardwareInfoClient;
        this.modelMapper = modelMapper;
        this.repository = motherMachineRepository;
        this.diskRepository = diskRepository;
        this.memoryRepository = memoryRepository;
    }

    public MotherMachine getMotherMachine() {

        if (repository.findAll().isEmpty())
            throw new NotFoundException("Mother Machine not found.");

        return repository.findAll().get(0);
    }

    public MotherMachine createMotherMachine() {

        if (!repository.findAll().isEmpty())
            throw new BadRequestException("There is already a machine created, use it or delete it to create another " +
                    "one.");

        return setMotherMachineProperties();
    }

    private MotherMachine setMotherMachineProperties() {
        Memory memory;
        Disk disk;
        MotherMachine motherMachine = modelMapper.map(myHardwareInfoClient.getHardwareInformation(), MotherMachine.class);

        memory = motherMachine.getMemory();
        disk = motherMachine.getDisk();
        motherMachine.setMemory(memoryRepository.save(memory));
        motherMachine.setDisk(diskRepository.save(disk));
        return repository.save(motherMachine);
    }
}
