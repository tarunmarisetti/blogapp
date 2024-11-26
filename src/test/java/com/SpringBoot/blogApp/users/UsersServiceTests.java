package com.SpringBoot.blogApp.users;

import com.SpringBoot.blogApp.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {
    @Autowired
    private UsersService usersService;
    @Test
    void can_create_users(){
        var user=usersService.createUser(new CreateUserRequest("john","pass123","john@gmail.com"));

        Assertions.assertEquals("john",user.getUsername());
    }
}
