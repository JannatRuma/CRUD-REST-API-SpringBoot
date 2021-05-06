package com.example.crudrestapi.repository;

import com.example.crudrestapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByUsername(String username);
    Author findByEmail(String email);
}
