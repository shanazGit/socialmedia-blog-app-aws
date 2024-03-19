package com.springboot.rta.socialmedia_app.service;

import com.springboot.rta.socialmedia_app.dto.LoginDto;
import com.springboot.rta.socialmedia_app.dto.RegisterDto;

public interface AuthService {
    //login
    String logIn(LoginDto loginDto);
    //register
    String register(RegisterDto registerDto);
}
