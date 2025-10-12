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
        Optional<Users> user = userService.getUserByEmail(userEmail);
        if(user.isEmpty()) return ResponseEntity.notFound().build();
        Optional<TeamResponseDto> teamResponseDto  = teamService.createTeam(teamRequestDto,user.get());
        return ResponseEntity.ok(teamResponseDto.get());
    }

    @PutMapping("/{teamId}/adduser")
    public ResponseEntity<String> addUserToTeam(@PathVariable long teamId, @RequestParam String userEmail) {
        Optional<Teams> team = teamService.getTeamById(teamId);
        Optional<Users> user = userService.getUserByEmail(userEmail);
        if(team.isEmpty() || user.isEmpty()) return ResponseEntity.badRequest().build();
        teamMembershipService.addUserToTeam(user.get(),team.get(), Role.USER);
        return ResponseEntity.ok("User Added to the team");
    }

}
