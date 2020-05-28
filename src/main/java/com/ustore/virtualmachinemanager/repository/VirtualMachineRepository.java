package com.ustore.virtualmachinemanager.repository;

import com.ustore.virtualmachinemanager.domain.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {

}
