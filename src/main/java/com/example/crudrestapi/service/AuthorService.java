package com.example.crudrestapi.service;

import com.example.crudrestapi.model.Author;
import com.example.crudrestapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Author findByUsername(String username) {
        return authorRepository.findByUsername(username);
    }
    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public boolean userExistByUsername(String username) {
        return authorRepository.findByUsername(username) != null;
    }
    public boolean userExistByEmail(String email) {
        return authorRepository.findByEmail(email) != null;
    }

    public Author createAuthor(Author author) {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        return authorRepository.save(author);
    }

}
