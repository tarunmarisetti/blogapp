package com.SpringBoot.blogApp.users;

import com.SpringBoot.blogApp.security.JWTService;
import com.SpringBoot.blogApp.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    public UsersService(ModelMapper modelMapper, UsersRepository usersRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public UsersEntity createUser(CreateUserRequest userRequest){
        UsersEntity user=modelMapper.map(userRequest,UsersEntity.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return usersRepository.save(user);
    }

    public UsersEntity getUserByName(String userName){
        return usersRepository.findByUsername(userName).orElseThrow(()-> new userNotFoundException(userName));
    }
    public UsersEntity getUserById(Long userId){
        return usersRepository.findById(userId).orElseThrow(()-> new userNotFoundException(userId));
    }

    public UsersEntity logInUser(String userName, String password) throws InvalidCredentialsException {
        var user= usersRepository.findByUsername(userName).orElseThrow(()-> new userNotFoundException(userName));
        var passwordMatches=passwordEncoder.matches(password,user.getPassword());
        if(!passwordMatches){
            throw new InvalidCredentialsException();
        }
        return user;
    }

    public static class userNotFoundException extends IllegalArgumentException{
        public userNotFoundException(String userName){
            super("User with userName:"+userName+" not found");
        }
        public userNotFoundException(Long userId){
            super("User with userId:"+userId+" not found");
        }
    }
    public static class InvalidCredentialsException extends IllegalArgumentException{
        public InvalidCredentialsException(){
            super("Invalid username or password");

        }
    }
}
