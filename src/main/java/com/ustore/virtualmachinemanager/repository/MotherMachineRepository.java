package com.ustore.virtualmachinemanager.repository;

import com.ustore.virtualmachinemanager.domain.MotherMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherMachineRepository extends JpaRepository<MotherMachine, Long> {

}
