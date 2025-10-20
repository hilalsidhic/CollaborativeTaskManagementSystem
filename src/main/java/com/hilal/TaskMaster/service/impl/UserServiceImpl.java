package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.entity.dto.UserDto;
import com.hilal.TaskMaster.entity.dto.UserUpdateDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
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

    private UserDto buildDtoFromUser(Users user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .age(user.getAge())
                .location(user.getLocation())
                .lastLogin(user.getLastLogin())
                .build();
    }
    @Override
    public boolean createUser(UserAuthenticationDTO userDTO) {
        if(userDTO.getUserName() == null || userDTO.getUserName().isEmpty()|| userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new BadRequestException("Username and Email cannot be null or empty");
        }
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
    public Users getUserByUserName(String username) {
        if(username == null || username.isEmpty()) {
            return null;
        }
        return userRepository.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public boolean updateLastLogin(Users user, LocalDateTime lastLogin) {
        if(user == null) {
            throw new BadRequestException("User cannot be null");
        }
        user.setLastLogin(lastLogin);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto updateUserDetails(Users existingUser, UserUpdateDto userUpdateDto) {
        if(existingUser == null) {
            throw new BadRequestException("Existing user cannot be null");
        }
        if(userUpdateDto.getUserName() != null && !userUpdateDto.getUserName().isEmpty()) {
            existingUser.setUserName(userUpdateDto.getUserName());
        }
        if (userUpdateDto.getAge() != 0) {
            existingUser.setAge(userUpdateDto.getAge());
        }
        if(userUpdateDto.getLocation() != null && !userUpdateDto.getLocation().isEmpty()) {
            existingUser.setLocation(userUpdateDto.getLocation());
        }
        existingUser.setUpdatedAt(LocalDateTime.now());
        Users savedUser = userRepository.save(existingUser);
        return buildDtoFromUser(savedUser);
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public Users getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
