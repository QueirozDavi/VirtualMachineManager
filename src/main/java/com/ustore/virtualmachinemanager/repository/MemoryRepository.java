package com.ustore.virtualmachinemanager.repository;

import com.ustore.virtualmachinemanager.domain.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {

}
