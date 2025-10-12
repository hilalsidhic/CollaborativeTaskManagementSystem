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
        Optional<Users> user = userService.getUserByEmail(email);
        if(user==null) return ResponseEntity.badRequest().build();
        UserDto userDto = UserDto.builder()
                .userName(user.get().getUserName())
                .email(user.get().getEmail())
                .age(user.get().getAge())
                .location(user.get().getLocation())
                .lastLogin(user.get().getLastLogin())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateUserProfile(Authentication authentication, @RequestBody UserUpdateDto userUpdateDto) {
        String email = authentication.getName();
        Optional<Users> user = userService.getUserByEmail(email);
        if(user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.updateUserDetails(user.get(),userUpdateDto));
    }
}
