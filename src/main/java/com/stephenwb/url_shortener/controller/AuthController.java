package com.stephenwb.url_shortener.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephenwb.url_shortener.dto.LoginRequest;
import com.stephenwb.url_shortener.dto.RegisterRequest;
import com.stephenwb.url_shortener.model.User;
import com.stephenwb.url_shortener.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/auth/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        // create new user object

        User user = new User();
        // set username and email from registerRequest

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());

        // hash password and bcrypt it
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        // userRepository.save(user)
        userRepository.save(user);

        // return success message

        return ("User registered successfully");
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null) {
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // make a JWT token
            String token = Jwts.builder()
                    .subject(user.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                    .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .compact();
            return ResponseEntity.ok(token);

        } else {
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

    }

}
