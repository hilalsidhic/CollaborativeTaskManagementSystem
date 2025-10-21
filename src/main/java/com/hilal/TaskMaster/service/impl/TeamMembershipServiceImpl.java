package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.TeamMemberships;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.repository.TeamMembershipRepository;
import com.hilal.TaskMaster.service.TeamMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TeamMembershipServiceImpl implements TeamMembershipService {
    private final TeamMembershipRepository teamMembershipRepository;

    @Override
    public void addUserToTeam(Users user, Teams team, Role role) {
        if(user == null || team == null || role == null) {
            throw new BadRequestException("User, Team, and Role must not be null");
        }
        TeamMemberships membership = TeamMemberships.builder()
                .user(user)
                .team(team)
                .role(role)
                .joinedAt(LocalDateTime.now())
                .build();
        teamMembershipRepository.save(membership);
        return;
    }
}
