package com.br.idonate.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.idonate.model.Role;
import com.br.idonate.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TokenService {

    @Value("${api.security.token.secret")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

//            List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

            String token = JWT.create().withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
//                    .withClaim("roles", roles)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException jwtCreationException) {
            throw new RuntimeException("Error while authenticating;");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException jwtVerificationException) {
            return null;
        }
    }

}
