package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.entity.dto.UserDto;
import com.hilal.TaskMaster.entity.dto.UserUpdateDto;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {
    boolean createUser(UserAuthenticationDTO userDTO);
    Optional<Users> getUserByUserName(String username);
    boolean updateLastLogin(Users user, LocalDateTime lastLogin);
    UserDto updateUserDetails(Users user, UserUpdateDto userUpdateDto);
    Optional<Users> getUserByEmail(String email);
    Optional<Users> getUserById(long userId);
}
