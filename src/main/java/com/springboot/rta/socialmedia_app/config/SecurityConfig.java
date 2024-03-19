package com.springboot.rta.socialmedia_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> csrf.disable())

                .authorizeHttpRequests(authorize  -> authorize.anyRequest().authenticated())
                //.authorizeHttpRequests(authorize  -> authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .httpBasic(Customizer.withDefaults());
        return http.build();}
          @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
        }

         /* @Bean
          public UserDetailsService userDetailsService(){
              UserDetails shanaz= User.builder()
                      .username("shanaz")
                      .password(passwordEncoder().encode("Shan12"))
                      .roles("User")
                      .build();

              UserDetails admin= User.builder()
                      .username("admin")
                      .password(passwordEncoder().encode("admin"))
                      .roles("ADMIN")
                      .build();
              return new InMemoryUserDetailsManager(shanaz,admin);


          }*/



}
