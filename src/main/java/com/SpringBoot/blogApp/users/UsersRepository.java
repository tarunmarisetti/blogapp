package com.SpringBoot.blogApp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    Optional<UsersEntity> findByUsername(String userName);
}
