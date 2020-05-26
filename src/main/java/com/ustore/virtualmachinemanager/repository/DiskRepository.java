package com.ustore.virtualmachinemanager.repository;

import com.ustore.virtualmachinemanager.domain.Disk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskRepository extends JpaRepository<Disk, Long> {

}
