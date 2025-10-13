package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
    Optional<Tasks> findById(Long id);
}
