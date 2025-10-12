package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TeamRequestDto;
import com.hilal.TaskMaster.entity.dto.TeamResponseDto;

import java.util.Optional;

public interface TeamService {
    Optional<TeamResponseDto> createTeam(TeamRequestDto teamRequestDto, Users user);
    Optional<Teams> getTeamById(long teamId);
    Optional<TeamResponseDto> updateTeam(long teamId, TeamRequestDto teamRequestDto);
}
