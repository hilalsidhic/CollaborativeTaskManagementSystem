package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.entity.dto.UserDto;
import com.hilal.TaskMaster.entity.dto.UserUpdateDto;
import com.hilal.TaskMaster.repository.UserRepository;
import com.hilal.TaskMaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean createUser(UserAuthenticationDTO userDTO) {
        LocalDateTime now = LocalDateTime.now();
        Users newUser = Users.builder()
                        .userName(userDTO.getUserName())
                        .email(userDTO.getEmail())
                        .createdAt(now)
                        .updatedAt(now)
                        .lastLogin(null)
                        .build();
        userRepository.save(newUser);
        return true;
    }

    @Override
    public Optional<Users> getUserByUserName(String username) {
        if(username == null || username.isEmpty()) {
            return null;
        }
        Optional<Users> user = userRepository.findByUserName(username);
        return user;
    }

    @Override
    public boolean updateLastLogin(Users user, LocalDateTime lastLogin) {
        user.setLastLogin(lastLogin);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto updateUserDetails(Users existingUser, UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getAge() != 0) {
            existingUser.setAge(userUpdateDto.getAge());
        }
        if(userUpdateDto.getLocation() != null && !userUpdateDto.getLocation().isEmpty()) {
            existingUser.setLocation(userUpdateDto.getLocation());
        }
        existingUser.setUpdatedAt(LocalDateTime.now());
        Users savedUser = userRepository.save(existingUser);
        return UserDto.builder()
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .age(savedUser.getAge())
                .location(savedUser.getLocation())
                .lastLogin(savedUser.getLastLogin())
                .build();
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public Optional<Users> getUserById(long userId) {
        Optional<Users> user = userRepository.findById(userId);
        return user;
    }
}
