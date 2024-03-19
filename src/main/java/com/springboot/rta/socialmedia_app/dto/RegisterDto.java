package com.springboot.rta.socialmedia_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String name;
    private String email;
    private String username;
    private String password;
}
