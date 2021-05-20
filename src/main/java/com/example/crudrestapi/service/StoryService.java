package com.example.crudrestapi.service;

import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    private final StoryRepository repository;

    public StoryService(final StoryRepository repository) {
        this.repository = repository;
    }

    public Story createStory(Story story, UserDetails userDetails) {
        story.setAuthorUsername(userDetails.getUsername());
        return repository.save(story);
    }
    public List<Story> getStories() {
        return repository.findAll();
    }
    public Optional<Story> getStoryById(int id) {
        return repository.findById(id);
    }

    public Story editStoryById(int id, Story story, UserDetails userDetails) throws Exception {
        Optional<Story> currentStory = repository.findById(id);
        if (currentStory.isPresent()) {
            if (!currentStory.get().getAuthorUsername().equals(userDetails.getUsername())) {
                throw new AccessDeniedException("You are not allowed to edit the file");
            }
            return currentStory.map(cs -> {
                cs.setTitle(story.getTitle());
                cs.setDescription(story.getDescription());
                cs.setAuthorUsername(userDetails.getUsername());
                return repository.save(cs);
            }).orElseGet(() -> null);
        }
        return null;
    }
    public String deleteStoryById(int id, UserDetails userDetails) {
        Optional <Story> currentStory = repository.findById(id);
        if (currentStory.isPresent()) {
            if (!userDetails.getUsername().equals(currentStory.get().getAuthorUsername())) {
                return "You are not allowed to delete this file";
            }
            currentStory.map(cs -> {
                repository.deleteById(id);
                return "Story with id: " + id + " is deleted.";});
        }
        return "Story Not Found";
    }
}
