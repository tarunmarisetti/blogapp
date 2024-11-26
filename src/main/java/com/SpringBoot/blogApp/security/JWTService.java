package com.SpringBoot.blogApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private static final String JWT_KEY="fgt7687uydcfvgbhnjmplkjhgfd387";
    private final Algorithm algorithm=Algorithm.HMAC256(JWT_KEY);

    public String createJWT(Long userId){
        return JWT.create().withSubject(userId.toString()).withIssuedAt(new Date())
//                .withExpiresAt(new Date().)
                .sign(algorithm);
    }
    public Long retrieveUserId(String jwtString){
        var decodedJWT=JWT.decode(jwtString);
        return Long.valueOf(decodedJWT.getSubject());
    }
}
