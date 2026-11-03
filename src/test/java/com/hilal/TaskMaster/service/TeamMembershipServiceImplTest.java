package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.TeamMemberships;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ForbiddenException;
import com.hilal.TaskMaster.repository.TeamMembershipRepository;
import com.hilal.TaskMaster.service.impl.TeamMembershipServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
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


    @Test
    public void testAdminCanAddMember() {
        Users admin = Users.builder().userId(10L).build();
        Users newUser = Users.builder().userId(20L).build();
        Teams team = Teams.builder().teamId(1L).build();
        TeamMemberships adminMembership = TeamMemberships.builder()
                .user(admin).team(team).role(Role.ADMIN).build();
        Mockito.when(teamMembershipRepository.findByTeam_TeamIdAndUser_UserId(1L, 10L))
                .thenReturn(Optional.of(adminMembership));

        teamMembershipService.addUserToTeam(admin, newUser, team, Role.USER);

        Mockito.verify(teamMembershipRepository).save(Mockito.argThat(membership ->
                membership.getUser().equals(newUser)
                        && membership.getTeam().equals(team)
                        && membership.getRole() == Role.USER));
    }

    @Test
    public void testManagerCanAddMember() {
        Users manager = Users.builder().userId(10L).build();
        Users newUser = Users.builder().userId(20L).build();
        Teams team = Teams.builder().teamId(1L).build();
        TeamMemberships managerMembership = TeamMemberships.builder()
                .user(manager).team(team).role(Role.MANAGER).build();
        Mockito.when(teamMembershipRepository.findByTeam_TeamIdAndUser_UserId(1L, 10L))
                .thenReturn(Optional.of(managerMembership));

        teamMembershipService.addUserToTeam(manager, newUser, team, Role.USER);

        Mockito.verify(teamMembershipRepository).save(Mockito.any(TeamMemberships.class));
    }

    @Test
    public void testRegularMemberCannotAddMember() {
        Users member = Users.builder().userId(10L).build();
        Users newUser = Users.builder().userId(20L).build();
        Teams team = Teams.builder().teamId(1L).build();
        TeamMemberships memberMembership = TeamMemberships.builder()
                .user(member).team(team).role(Role.DEVELOPER).build();
        Mockito.when(teamMembershipRepository.findByTeam_TeamIdAndUser_UserId(1L, 10L))
                .thenReturn(Optional.of(memberMembership));

        Assertions.assertThrows(ForbiddenException.class,
                () -> teamMembershipService.addUserToTeam(member, newUser, team, Role.USER));
        Mockito.verify(teamMembershipRepository, Mockito.never()).save(Mockito.any(TeamMemberships.class));
    }

    @Test
    public void testNonMemberCannotAddMember() {
        Users nonMember = Users.builder().userId(10L).build();
        Users newUser = Users.builder().userId(20L).build();
        Teams team = Teams.builder().teamId(1L).build();
        Mockito.when(teamMembershipRepository.findByTeam_TeamIdAndUser_UserId(1L, 10L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ForbiddenException.class,
                () -> teamMembershipService.addUserToTeam(nonMember, newUser, team, Role.USER));
        Mockito.verify(teamMembershipRepository, Mockito.never()).save(Mockito.any(TeamMemberships.class));
    }

}
