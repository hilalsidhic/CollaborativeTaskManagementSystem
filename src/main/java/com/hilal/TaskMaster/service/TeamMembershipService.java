package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Role;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;

public interface TeamMembershipService {
    void addUserToTeam(Users user, Teams team, Role role);
}
