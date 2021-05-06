package com.example.crudrestapi.service;

import com.example.crudrestapi.model.Author;
import com.example.crudrestapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = repository.findByUsername(username);
        if (author == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return new User(author.getUsername(), author.getPassword(), new ArrayList<>());
    }
}
