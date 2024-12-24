//package com.lawal.banji.springkitchen.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth
//                    -> auth.requestMatchers("/h2-console/**", "kitchen/pantry/**", "kitchen/recipe/**")
//            .authenticated()
//            .anyRequest()
//            .permitAll()
//        ).build();
//    }
//
//}
