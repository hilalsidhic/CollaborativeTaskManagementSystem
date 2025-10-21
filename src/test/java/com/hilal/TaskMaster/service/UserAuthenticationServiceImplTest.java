package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.UserAuthentication;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ConflictException;
import com.hilal.TaskMaster.repository.UserAuthenticationRepository;
import com.hilal.TaskMaster.service.impl.UserAuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceImplTest {

    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationService;


    @Test
    public void testRegisterUser() {
        UserAuthenticationDTO userDTO = UserAuthenticationDTO.builder()
                .email("testuser")
                .password("password123")
                .build();
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        boolean result = userAuthenticationService.registerUser(userDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        Mockito.when(userAuthenticationRepository.findByEmail("existinguser")).thenReturn(Optional.of(new UserAuthentication()));
        UserAuthenticationDTO userDTO = UserAuthenticationDTO.builder()
                .email("existinguser")
                .password("password123")
                .build();
        Assertions.assertThrows(ConflictException.class, () -> {
            userAuthenticationService.registerUser(userDTO);
        });
    }

    @Test
    public void testRegisterUser_NullOrEmptyPassword() {
        UserAuthenticationDTO userDTO = UserAuthenticationDTO.builder()
                .email("testuser")
                .password("")
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            userAuthenticationService.registerUser(userDTO);
        });
    }
}
