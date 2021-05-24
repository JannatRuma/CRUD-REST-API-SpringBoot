package com.example.crudrestapi.service;

import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.repository.StoryRepository;
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

    public Story createStory(Story story) {
        return repository.save(story);
    }
    public List<Story> getStories() {
        return repository.findAll();
    }
    public Story getStoryById(int id) {
        Optional <Story> story = repository.findById(id);
        return story.orElse(null);
    }

    public Story editStoryById(Story story, Story existingStory) {
        if (story.getTitle() != null) existingStory.setTitle(story.getTitle());
        if (story.getDescription() != null) existingStory.setDescription(story.getDescription());
        return repository.save(existingStory);
    }
    public String deleteStoryById(int id) {
        repository.deleteById(id);
        return "Story with id: " + id + " is successfully deleted!";
    }
}
