package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TeamRequestDto;
import com.hilal.TaskMaster.entity.dto.TeamResponseDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
import com.hilal.TaskMaster.repository.TeamRepository;
import com.hilal.TaskMaster.service.TeamMembershipService;
import com.hilal.TaskMaster.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembershipService teamMembershipService;

    private TeamResponseDto buildDtoFromTeam(Teams team) {
        return TeamResponseDto.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .description(team.getDescription())
                .build();
    }

    @Override
    public TeamResponseDto createTeam(TeamRequestDto teamRequestDto, Users user) {
        if(teamRequestDto.getTeamName() == null || teamRequestDto.getTeamName().isEmpty()) {
            throw new BadRequestException("Team name cannot be null or empty");
        }
        if(user == null) {
            throw new BadRequestException("User cannot be null");
        }
        Teams team = Teams.builder()
                .teamName(teamRequestDto.getTeamName())
                .description(teamRequestDto.getDescription())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teams savedTeam = teamRepository.save(team);
        teamMembershipService.addUserToTeam(user,savedTeam, Role.ADMIN);

        return buildDtoFromTeam(savedTeam);
    }

    @Override
    public Teams getTeamById(long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(()-> new ResourceNotFoundException("Team not found with id: " + teamId));
    }

    @Override
    public TeamResponseDto updateTeam(long teamId, TeamRequestDto teamRequestDto) {
        return null;
    }
}
