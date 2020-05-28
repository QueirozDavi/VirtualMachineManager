package com.ustore.virtualmachinemanager.controller.v1;

import com.ustore.virtualmachinemanager.domain.VirtualMachine;
import com.ustore.virtualmachinemanager.domain.dto.VirtualMachineRequestDTO;
import com.ustore.virtualmachinemanager.service.VirtualMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/virtual-machine")
public class VirtualMachineController {

    private final VirtualMachineService virtualMachineService;

    @Autowired
    public VirtualMachineController(VirtualMachineService virtualMachineService) {
        this.virtualMachineService = virtualMachineService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMotherMachine(@Valid @RequestBody VirtualMachineRequestDTO virtualMachineRequestDTO){
        virtualMachineService.createVirtualMachine(virtualMachineRequestDTO);
    }
}
