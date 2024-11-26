package com.SpringBoot.blogApp.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void can_create_user(){
        UsersEntity user=UsersEntity.builder().username("user").email("test@gmail.com").build();
        usersRepository.save(user);
    }

}
