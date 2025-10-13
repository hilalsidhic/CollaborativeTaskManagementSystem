package com.hilal.TaskMaster.repository;

import com.hilal.TaskMaster.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long id);
}
