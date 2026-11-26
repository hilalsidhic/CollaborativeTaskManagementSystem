package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.TeamMemberships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMemberships, Long> {

    Optional<TeamMemberships> findByTeam_TeamIdAndUser_UserId(long teamId, long userId);

}
