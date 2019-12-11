package com.irfan.springsecurityjwt.service;

import com.irfan.springsecurityjwt.UserDetailsRepository;
import com.irfan.springsecurityjwt.model.MyUserDetails;
import com.irfan.springsecurityjwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userDetailsRepository.findByUserName(userName);
        user.orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return user.map(MyUserDetails::new).get();
    }
}
