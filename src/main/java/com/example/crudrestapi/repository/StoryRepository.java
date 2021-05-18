package com.example.crudrestapi.repository;

import com.example.crudrestapi.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Integer> {

}
