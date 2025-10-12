package com.hilal.TaskMaster.controller;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.entity.dto.UserLoginResponseDTO;
import com.hilal.TaskMaster.security.JwtService;
import com.hilal.TaskMaster.service.UserAuthenticationService;
import com.hilal.TaskMaster.service.UserService;
import com.hilal.TaskMaster.service.impl.UserAuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthenticationService userAuthenticationService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserAuthenticationDTO user) {
        boolean authRegistered = userAuthenticationService.registerUser(user);
        boolean userCreated = authRegistered && userService.createUser(user);

        if (userCreated) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.badRequest().body("User registration failed");
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserAuthenticationDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword())
        );
        UserDetails user = userDetailsService.loadUserByUsername(userDTO.getEmail());
        if(user == null){
            return ResponseEntity.internalServerError().build();
        }
        Optional<Users> userdetails = userService.getUserByEmail(userDTO.getEmail());
        userService.updateLastLogin(userdetails.get(), LocalDateTime.now());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new UserLoginResponseDTO(token));
    }

}
