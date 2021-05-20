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
        return service.getStoryById(id)
                .map(story -> new ResponseEntity<>(story, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/story")
    public ResponseEntity<?> createStory(@RequestBody Story story, Authentication authentication) {
        if (story.getAuthorUsername() != null) {
            return new ResponseEntity<>("You should not include the author username in the request body!! Login first", HttpStatus.UNAUTHORIZED);
        }
        Story createdStory = service.createStory(story, (UserDetails) authentication.getPrincipal());
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @PutMapping("/story/{id}")
    public ResponseEntity<Story> editStoryById(@PathVariable int id, @RequestBody Story story, Authentication authentication) throws Exception{
        Optional <Story> updatedStory = Optional.ofNullable(service.editStoryById(id, story, (UserDetails) authentication.getPrincipal()));
        return updatedStory.map(us -> new ResponseEntity<>(us, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/story/{id}")
    public ResponseEntity<?> deleteStoryById(@PathVariable int id, Authentication authentication) {
        String storyDeleteMsg = service.deleteStoryById(id, (UserDetails) authentication.getPrincipal());
        if (storyDeleteMsg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (storyDeleteMsg.equals("You are not allowed to delete this file")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(storyDeleteMsg, HttpStatus.OK);
    }
}
