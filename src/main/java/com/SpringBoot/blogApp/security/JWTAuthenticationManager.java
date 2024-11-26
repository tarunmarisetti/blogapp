package com.SpringBoot.blogApp.security;

import com.SpringBoot.blogApp.users.UsersEntity;
import com.SpringBoot.blogApp.users.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationManager implements AuthenticationManager {
    private final JWTService jwtService;
    private final UsersService usersService;

    public JWTAuthenticationManager(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication){
            var jwtAuthentication=(JWTAuthentication) authentication;
            var jwt=jwtAuthentication.getCredentials();
            var userId=jwtService.retrieveUserId(jwt);

            UsersEntity usersEntity=usersService.getUserById(userId);
            jwtAuthentication.usersEntity=usersEntity;
            jwtAuthentication.setAuthenticated(true);
            return jwtAuthentication;
        }
        throw  new IllegalAccessError("Cannot Authenticate with non-JWT Authentication");
    }
}
