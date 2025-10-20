package com.hilal.TaskMaster.controller;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TeamRequestDto;
import com.hilal.TaskMaster.entity.dto.TeamResponseDto;
import com.hilal.TaskMaster.service.TeamMembershipService;
import com.hilal.TaskMaster.service.TeamService;
import com.hilal.TaskMaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamsController {

    private final UserService userService;
    private final TeamService teamService;
    private final TeamMembershipService teamMembershipService;

    @PostMapping("/")
    public ResponseEntity<TeamResponseDto> createTeam(Authentication authentication, @RequestBody TeamRequestDto teamRequestDto) {
        String userEmail = authentication.getName();
        Users user = userService.getUserByEmail(userEmail);
        TeamResponseDto teamResponseDto  = teamService.createTeam(teamRequestDto,user);
        return ResponseEntity.ok(teamResponseDto);
    }

    @PutMapping("/{teamId}/adduser")
    public ResponseEntity<String> addUserToTeam(@PathVariable long teamId, @RequestParam String userEmail) {
        Teams team = teamService.getTeamById(teamId);
        Users user = userService.getUserByEmail(userEmail);
        teamMembershipService.addUserToTeam(user,team, Role.USER);
        return ResponseEntity.ok("User Added to the team");
    }

}
