package com.SpringBoot.blogApp.security;

import com.SpringBoot.blogApp.users.UsersEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthentication implements Authentication {
    String jwt;
    UsersEntity usersEntity;

    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /*
    returns the credentials of the authentication request
    eg: password , bearer token or cookie
     */
    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }
    /*
    principle is the entity that is being authenticated
    in this case it is user
     */
    @Override
    public UsersEntity getPrincipal() {
        return usersEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return usersEntity!=null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
