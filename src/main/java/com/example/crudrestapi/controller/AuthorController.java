package com.example.crudrestapi.controller;

import com.example.crudrestapi.model.AuthenticationRequest;
import com.example.crudrestapi.model.AuthenticationResponse;
import com.example.crudrestapi.model.Author;
import com.example.crudrestapi.service.AuthorService;
import com.example.crudrestapi.service.LoginUserService;
import com.example.crudrestapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("/register")
    public String getAuthors() {
        return ("<h1>Authors</h1>");
    }
    @PostMapping("/register")
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        if (service.userExistByUsername(author.getUsername())) {
            return new ResponseEntity<>("Username already exists",HttpStatus.CONFLICT);
        }
        if (service.userExistByEmail(author.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
        service.createAuthor(author);
        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }
    @GetMapping("/login")
    public String get() {
        return ("<h1>Login</h1>");
    }
    @PostMapping("/login")
    public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch(BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password ", e);
        }
        final UserDetails userDetails = loginUserService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }
}
