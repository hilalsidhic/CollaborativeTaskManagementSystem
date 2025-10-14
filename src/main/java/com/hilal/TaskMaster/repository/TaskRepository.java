package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    Optional<Tasks> findById(Long id);
    List<Tasks> findByStatus(Status status);
    List<Tasks> findByAssignedToUserId(Long userId);
    List<Tasks> findByCreatedByUserId(Long userId);
    List<Tasks> findByAssignedToUserIdOrCreatedByUserId(Long assignedToId, Long createdById);
    void deleteById(Long id);
    List<Tasks> findByDueDateBefore(LocalDateTime dateTime);
    List<Tasks> findByTeamTeamId(Long teamId);
    List<Tasks> findByDueDateBeforeAndStatusNot(LocalDateTime dateTime, Status status);
}
