package com.springboot.rta.socialmedia_app.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodingDecodingUtility {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println("Shanaz::"+passwordEncoder.encode("Shan12"));
        System.out.println("Admin::"+passwordEncoder.encode("admin"));
}}
