package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Teams, Long> {
    Optional<Teams> findById(Long id);
}
