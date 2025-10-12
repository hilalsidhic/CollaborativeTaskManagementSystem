package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TeamRequestDto;
import com.hilal.TaskMaster.entity.dto.TeamResponseDto;
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

    @Override
    public Optional<TeamResponseDto> createTeam(TeamRequestDto teamRequestDto, Users user) {
        Teams team = Teams.builder()
                .teamName(teamRequestDto.getTeamName())
                .description(teamRequestDto.getDescription())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teams savedTeam = teamRepository.save(team);
        teamMembershipService.addUserToTeam(user,savedTeam, Role.ADMIN);

        TeamResponseDto responseDto = TeamResponseDto.builder()
                .teamId(savedTeam.getTeamId())
                .teamName(savedTeam.getTeamName())
                .description(savedTeam.getDescription())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<Teams> getTeamById(long teamId) {
        Optional<Teams> team = teamRepository.findById(teamId);
        if (team.isEmpty()) {
            return Optional.empty();
        }
        return team;
    }

    @Override
    public Optional<TeamResponseDto> updateTeam(long teamId, TeamRequestDto teamRequestDto) {
        return Optional.empty();
    }
}
