package com.palla.Insta_Clone.Config;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

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
                .authorizeHttpRequests((request->request.requestMatchers(HttpMethod.GET, "/spin").permitAll().anyRequest().authenticated()))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenValidationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtTokenGeneratorFilter, BasicAuthenticationFilter.class)
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();
//				cfg.setAllowedOrigins(Collections.singletonList("*"));
                        cfg.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000",
                                "http://localhost:4000"));
                        //cfg.setAllowedMethods(Arrays.asList("GET", "POST","DELETE","PUT"));
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                })// Custom filter can be added here;
                .and()
                .formLogin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
