package com.SpringBoot.blogApp.security;

import com.SpringBoot.blogApp.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class AppSecurityConfig  {
    private final JWTService jwtService;
    private final UsersService usersService;

    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }
    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter(){
        return  new JWTAuthenticationFilter(new JWTAuthenticationManager(jwtService,usersService));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Replace deprecated cors().and()
                .csrf(csrf -> csrf.disable())  // Disable CSRF for non-browser clients (e.g., Postman, curl)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class);

        return http.build();
    }
}
