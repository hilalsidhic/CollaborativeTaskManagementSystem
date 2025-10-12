package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;

public interface UserAuthenticationService {
    boolean registerUser(UserAuthenticationDTO userDTO);
}
