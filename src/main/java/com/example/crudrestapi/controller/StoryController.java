package com.example.crudrestapi.controller;

import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
public class StoryController {

    private final StoryService service;

    public StoryController(final StoryService service) {
        this.service = service;
    }

    @RequestMapping(value = "/story",
            produces = { "application/json", "application/xml" },
            method = RequestMethod.GET)
    public List<Story> getStories() {
        return service.getStories();
    }

    @RequestMapping(value = "/story/{id}",
            produces = { "application/json", "application/xml" },
            method = RequestMethod.GET)
    public ResponseEntity<Story> getStory(@PathVariable int id) {
        Story story = service.getStoryById(id);
        if (story == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    @PostMapping("/story")
    public ResponseEntity<?> createStory(@RequestBody Story story, Authentication authentication) {
        if (story.getAuthorUsername() != null) {
            return new ResponseEntity<>("You should not include the author username in the request body!! Login first", HttpStatus.UNAUTHORIZED);
        }
        story.setAuthorUsername(((UserDetails)authentication.getPrincipal()).getUsername());
        Story createdStory = service.createStory(story);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @PutMapping("/story/{id}")
    public ResponseEntity<Story> editStoryById(@PathVariable int id, @RequestBody Story story, Authentication authentication) throws Exception{
        Story existingStory = service.getStoryById(id);
        if (existingStory == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!existingStory.getAuthorUsername().equals(((UserDetails)authentication.getPrincipal()).getUsername())) {
            throw new AccessDeniedException("You are not allowed to edit the file");
        }
        Story updatedStory = service.editStoryById(story, existingStory);
        return new ResponseEntity<>(updatedStory, HttpStatus.OK);

    }

    @DeleteMapping("/story/{id}")
    public ResponseEntity<?> deleteStoryById(@PathVariable int id, Authentication authentication) {
        Story existingStory = service.getStoryById(id);
        if (existingStory == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String loggedInUsername = ((UserDetails)authentication.getPrincipal()).getUsername();
        String existingStoryAuthor = existingStory.getAuthorUsername();

        if (!existingStoryAuthor.equals(loggedInUsername)) {
            return new ResponseEntity<>("You are not allowed to delete this story", HttpStatus.UNAUTHORIZED);
        }
        String deleteAndGetMsg = service.deleteStoryById(id);
        return new ResponseEntity<>(deleteAndGetMsg, HttpStatus.OK);

    }
}
