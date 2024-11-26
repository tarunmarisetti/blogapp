package com.SpringBoot.blogApp.users;

import com.SpringBoot.blogApp.common.dtos.ErrorResponse;
import com.SpringBoot.blogApp.security.JWTService;
import com.SpringBoot.blogApp.users.dtos.CreateUserRequest;
import com.SpringBoot.blogApp.users.dtos.UserResponse;
import com.SpringBoot.blogApp.users.dtos.LogInUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> singUpUser(@RequestBody CreateUserRequest createUserRequest){
        UsersEntity savedUser =usersService.createUser(createUserRequest);
        URI savedUserURI=URI.create("/users/"+savedUser.getId());
        UserResponse savedUserResponse=modelMapper.map(savedUser, UserResponse.class);
        savedUserResponse.setToken(jwtService.createJWT(savedUser.getId()));
        return ResponseEntity.created(savedUserURI)
                .body(savedUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> logInUser(@RequestBody LogInUserRequest logInUserRequest){
        UsersEntity savedUser=usersService.logInUser(logInUserRequest.getUsername(),logInUserRequest.getPassword());
        UserResponse userResponse=modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.createJWT(userResponse.getId()));
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UsersService.userNotFoundException.class,
            UsersService.InvalidCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleUsersExceptions(Exception ex){
        HttpStatus status;
        String message;
        if(ex instanceof UsersService.userNotFoundException){
            status=HttpStatus.NOT_FOUND;
            message=ex.getMessage();
        }
        else if(ex instanceof UsersService.InvalidCredentialsException){
            status=HttpStatus.UNAUTHORIZED;
            message=ex.getMessage();
        }
        else {
            status=HttpStatus.INTERNAL_SERVER_ERROR;
            message="Something went wrong";
        }
        ErrorResponse errorResponse =ErrorResponse.builder().message(message).build();

        return ResponseEntity.status(status).body(errorResponse);
    }

}
