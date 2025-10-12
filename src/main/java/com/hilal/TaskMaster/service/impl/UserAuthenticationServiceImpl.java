package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.UserAuthentication;
import com.hilal.TaskMaster.entity.dto.UserAuthenticationDTO;
import com.hilal.TaskMaster.repository.UserAuthenticationRepository;
import com.hilal.TaskMaster.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(UserAuthenticationDTO userDTO) throws IllegalArgumentException {
        if (userAuthenticationRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        UserAuthentication userNew = new UserAuthentication();
        userNew.setHashPassword(passwordEncoder.encode(userDTO.getPassword()));
        userNew.setEmail(userDTO.getEmail());
        userAuthenticationRepository.save(userNew);
        return true;
    }

}
