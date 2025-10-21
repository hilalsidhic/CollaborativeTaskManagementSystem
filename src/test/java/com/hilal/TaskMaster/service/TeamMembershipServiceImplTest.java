package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.TeamMemberships;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.repository.TeamMembershipRepository;
import com.hilal.TaskMaster.service.impl.TeamMembershipServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TeamMembershipServiceImplTest {
    @Mock
    private TeamMembershipRepository teamMembershipRepository;

    @InjectMocks
    private TeamMembershipServiceImpl teamMembershipService;

    @Test
    public void testaddusertoteam_valid(){
        Users user = Users.builder().userName("testuser").userId(1L).age(21).email("testuser").build();
        Teams team = Teams.builder().teamName("testteam").teamId(1L).build();
        Role role = Role.TESTER;
        // Assert
        teamMembershipService.addUserToTeam(user,team,role);
        ArgumentCaptor<TeamMemberships> captor = ArgumentCaptor.forClass(TeamMemberships.class);
        Mockito.verify(teamMembershipRepository, Mockito.times(1)).save(captor.capture());

        TeamMemberships savedMembership = captor.getValue();
        Assertions.assertEquals(user, savedMembership.getUser());
        Assertions.assertEquals(team, savedMembership.getTeam());
        Assertions.assertEquals(role, savedMembership.getRole());
        Assertions.assertNotNull(savedMembership.getJoinedAt());
    }

    @Test
    public void testaddusertoteam_invalid(){
        Assertions.assertThrows(BadRequestException.class,()->{
            teamMembershipService.addUserToTeam(null,null,Role.TESTER);
        });
    }
}
