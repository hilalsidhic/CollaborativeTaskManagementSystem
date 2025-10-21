package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TeamRequestDto;
import com.hilal.TaskMaster.entity.dto.TeamResponseDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
import com.hilal.TaskMaster.repository.TeamRepository;
import com.hilal.TaskMaster.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMembershipService teamMembershipService;

    @InjectMocks
    private TeamServiceImpl teamServiceImpl;

    @Test
    public void testCreateTeam_valid() {
        Users user  =Users.builder().userId(1L).userName("testuser").build();
        TeamRequestDto teamRequestDto = TeamRequestDto.builder()
                .teamName("Test Team")
                .description("This is a test team")
                .build();
        Mockito.when(teamRepository.save(Mockito.any(Teams.class)))
                .thenAnswer(invocation -> {
                    Teams team = invocation.getArgument(0);
                    team.setTeamId(1L);
                    return team;
                });
        Mockito.doNothing().when(teamMembershipService)
                .addUserToTeam(Mockito.any(Users.class), Mockito.any(Teams.class), Mockito.any(Role.class));
        TeamResponseDto responseDto = teamServiceImpl.createTeam(teamRequestDto, user);
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals("Test Team", responseDto.getTeamName());
        Assertions.assertEquals("This is a test team", responseDto.getDescription());
    }

    @Test
    public void testCreateTeam_nullTeamName() {
        Users user  =Users.builder().userId(1L).userName("testuser").build();
        TeamRequestDto teamRequestDto = TeamRequestDto.builder()
                .teamName(null)
                .description("This is a test team")
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            teamServiceImpl.createTeam(teamRequestDto, user);
        });
    }

    @Test
    public void testCreateTeam_emptyTeamName() {
        Users user = Users.builder().userId(1L).userName("testuser").build();
        TeamRequestDto teamRequestDto = TeamRequestDto.builder()
                .teamName("")
                .description("This is a test team")
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            teamServiceImpl.createTeam(teamRequestDto, user);
        });
    }

    @Test
    public void testCreateTeam_nullUser() {
        TeamRequestDto teamRequestDto = TeamRequestDto.builder()
                .teamName("Test Team")
                .description("This is a test team")
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            teamServiceImpl.createTeam(teamRequestDto, null);
        });
    }

    @Test
    public void testgetteambyid_valid(){
        Teams team = Teams.builder().teamName("Test team").build();
        Mockito.when((teamRepository.findById(1L))).thenReturn(Optional.of(team));
        Teams teamResponse = teamServiceImpl.getTeamById(1L);
        Assertions.assertEquals(teamResponse.getTeamName(),"Test team");
    }

    @Test
    public void testgetteambyid_invalid(){
        Assertions.assertThrows(BadRequestException.class, () -> {
            teamServiceImpl.getTeamById(0L);
        });
    }

    @Test
    public void testgetteambyid_notfound(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            teamServiceImpl.getTeamById(1L);
        });
    }
}
