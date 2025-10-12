package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.TeamMemberships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMemberships, Long> {

}
