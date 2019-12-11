package com.irfan.springsecurityjwt.controller;

import com.irfan.springsecurityjwt.model.AuthenticationRequest;
import com.irfan.springsecurityjwt.model.AuthenticationResponse;
import com.irfan.springsecurityjwt.service.MyUserDetailsService;
import com.irfan.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @RequestMapping({"/hello"})
    public String userDetails(){
        return "Hello User";
    }
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException ex){
            throw new Exception("Incorrect credentials",ex);
        }
        
         final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
         final String jwt = jwtUtil.generateToken(userDetails);
         
         return  ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
