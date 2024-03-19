package com.springboot.rta.socialmedia_app.service.implementation;

import com.springboot.rta.socialmedia_app.dto.LoginDto;
import com.springboot.rta.socialmedia_app.dto.RegisterDto;
import com.springboot.rta.socialmedia_app.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    //private JwtTokenUtility
    @Override
    public String logIn(LoginDto loginDto) {
        //Authenticate the User using Spring Authentication Manager
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        //Set the Authentication object into SecurityContext Holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
       // String jwtToken=jwtTokenUtility.generateJwtToken(authentication);


        return "user logedin successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {
        return null;
    }
}
