package com.SpringBoot.blogApp.users.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

}
