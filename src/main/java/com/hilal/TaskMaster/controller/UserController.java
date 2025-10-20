package com.hilal.TaskMaster.controller;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserDto;
import com.hilal.TaskMaster.entity.dto.UserUpdateDto;
import com.hilal.TaskMaster.repository.UserRepository;
import com.hilal.TaskMaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        Users user = userService.getUserByEmail(email);
        UserDto userDto = UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .age(user.getAge())
                .location(user.getLocation())
                .lastLogin(user.getLastLogin())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateUserProfile(Authentication authentication, @RequestBody UserUpdateDto userUpdateDto) {
        String email = authentication.getName();
        Users user = userService.getUserByEmail(email);
        return ResponseEntity.ok(userService.updateUserDetails(user,userUpdateDto));
    }
}
