package com.palla.Insta_Clone.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;

    @Autowired
    private JwtTokenValidationFilter jwtTokenValidationFilter;
    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception{
        http.csrf(customizer->customizer.disable())
                .authorizeHttpRequests((request->request.requestMatchers(HttpMethod.POST, "/signup").permitAll().anyRequest().authenticated()))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtTokenGeneratorFilter, BasicAuthenticationFilter.class) // Custom filter can be added here
                .addFilterBefore(jwtTokenValidationFilter, BasicAuthenticationFilter.class)
                .formLogin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
