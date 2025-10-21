package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.entity.dto.UserUpdateDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
import com.hilal.TaskMaster.repository.UserRepository;
import com.hilal.TaskMaster.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testcreateuser_valid() {
        UserAuthenticationDTO user = UserAuthenticationDTO.builder()
                .userName("testuser")
                .email("test@gmail.com")
                .password("Test@1234")
                .build();
        boolean userValid = userService.createUser(user);
        Assertions.assertTrue(userValid);
    }

    @Test
    public void testcreateuser_invalid() {
        UserAuthenticationDTO user = new UserAuthenticationDTO();
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    public void testgetUserByUserName_valid() {
        UserAuthenticationDTO user = UserAuthenticationDTO.builder()
                .userName("testuser")
                .email("test@gmail.com")
                .password("Test@1234")
                .build();
        Users userReal = Users.builder()
                .userName("testuser")
                .email("test@gmail.com")
                .build();
        Mockito.when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(userReal));
        Users fetchedUser = userService.getUserByUserName("testuser");
        Assertions.assertNotNull(fetchedUser);
        Assertions.assertEquals("testuser", fetchedUser.getUserName());
    }

    @Test
    public void testgetUserByUserName_null() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.getUserByUserName(null);
        });
    }

    @Test
    public void testgetUserByUserName_invalid() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserByUserName("testuser");
        });
    }

    @Test
    public void testupdateLastLogin_valid() {
        Users userReal = Users.builder()
                .userName("testuser")
                .email("testuser")
                .build();
        boolean isUpdated = userService.updateLastLogin(userReal, null);
        Assertions.assertTrue(isUpdated);
    }

    @Test
    public void testupdateLastLogin_nullUser() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.updateLastLogin(null, null);
        });
    }

    @Test
    public void testupdateUserDetails_valid() {
        Users userReal = Users.builder()
                .userName("testuser")
                .email("testuser")
                .build();
        UserUpdateDto updateDto = UserUpdateDto.builder()
                .userName("updatedUser")
                .email("testuser")
                .age(25)
                .build();

        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var updatedUserDto = userService.updateUserDetails(userReal, updateDto);
        Assertions.assertNotNull(updatedUserDto);
        Assertions.assertEquals("updatedUser", updatedUserDto.getUserName());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(Users.class));
    }

    @Test
    public void testupdateUserDetails_nullUser() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.updateUserDetails(null, new UserUpdateDto());
        });
    }

    @Test
    public void testupdateUserDetails_nullUpdateDto() {
        Users userReal = Users.builder()
                .userName("testuser")
                .email("testuser")
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.updateUserDetails(userReal, null);
        });
    }

    @Test
    public void testgetUserByEmail_valid() {
        Users userReal = Users.builder()
                .userName("testuser")
                .email("testuser")
                .build();
        Mockito.when(userRepository.findByEmail("testuser")).thenReturn(Optional.of(userReal));
        Users fetchedUser = userService.getUserByEmail("testuser");
        Assertions.assertNotNull(fetchedUser);
        Assertions.assertEquals("testuser", fetchedUser.getEmail());
    }

    @Test
    public void testgetUserByEmail_null() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.getUserByEmail(null);
        });
    }

    @Test
    public void testgetUserByEmail_invalid() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserByEmail("testuser");
        });
    }

    @Test
    public void testgetUserById_valid() {
        Users userReal = Users.builder()
                .userName("testuser")
                .email("testuser")
                .build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userReal));
        Users fetchedUser = userService.getUserById(1L);
        Assertions.assertNotNull(fetchedUser);
        Assertions.assertEquals("testuser", fetchedUser.getUserName());
    }

    @Test
    public void testgetUserById_invalid() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.getUserById(-1);
        });
    }

    @Test
    public void testgetUserById_notFound() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
    }
}
