package com.irfan.springsecurityjwt;

import com.irfan.springsecurityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User,Integer> {
    
    Optional<User> findByUserName(String userName);
}
