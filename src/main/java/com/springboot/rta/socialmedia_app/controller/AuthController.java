package com.springboot.rta.socialmedia_app.controller;

import com.springboot.rta.socialmedia_app.dto.LoginDto;
import com.springboot.rta.socialmedia_app.dto.RegisterDto;
import com.springboot.rta.socialmedia_app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    //http-"/login","/signin"
    @PostMapping(value= {"/login","/signin"})
    public ResponseEntity<String>logIn(@RequestBody LoginDto loginDto){
       String response= authService.logIn(loginDto);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //http-"/register","/signup"
    @PostMapping(value= {"/register","/signup"})
    public ResponseEntity<String>register(@RequestBody RegisterDto registerDto){
        String response= authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
