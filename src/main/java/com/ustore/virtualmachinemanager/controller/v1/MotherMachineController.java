package com.ustore.virtualmachinemanager.controller.v1;

import com.ustore.virtualmachinemanager.domain.MotherMachine;
import com.ustore.virtualmachinemanager.domain.dto.HardwareInfoDTO;
import com.ustore.virtualmachinemanager.service.MotherMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/mother-machine")
public class MotherMachineController {

    private final MotherMachineService motherMachineService;

    @Autowired
    public MotherMachineController(MotherMachineService motherMachineService) {
        this.motherMachineService = motherMachineService;
    }

    @GetMapping
    public MotherMachine getMotherMachine(){
        return motherMachineService.getMotherMachine();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MotherMachine createMotherMachine(){
        return motherMachineService.createMotherMachine();
    }
}
