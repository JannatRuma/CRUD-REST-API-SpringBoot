package com.example.crudrestapi.controller;
import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.service.StoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

class StoryControllerTest {

    private final StoryService service = mock(StoryService.class);
    private final Authentication authentication = mock(Authentication.class);

    private final StoryController controller = new StoryController(service);

    @Test
    @DisplayName("Should Create Story with all valid information (Story Controller file)")
    void shouldCreateStory() {
        Story story = new Story(1, "Test Story", "Test Description");
        when(service.createStory(story, (UserDetails) authentication.getPrincipal()))
                .thenReturn(story);
        ResponseEntity<?> entity = controller.createStory(story, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);
        verify(service).createStory(story, (UserDetails) authentication.getPrincipal());
    }

    @Test
    @DisplayName("Should Not Create Story for including author username (Story Controller file)")
    void shouldNotCreateStoryForAuthorUsername() {
        Story story = new Story(1, "Test Story", "Test Description", "simanta");
        when(service.createStory(story, (UserDetails) authentication.getPrincipal()))
                .thenReturn(story);
        story.setAuthorUsername("simanta");
        ResponseEntity<?> entity = controller.createStory(story, authentication);
        assertEquals(entity.getBody(), "You should not include the author username in the request body!! Login first");
        assertEquals(entity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Should Edit Story with all valid information (Story Controller file)")
    void shouldEditStory() throws Exception {
        Story story = new Story(1, "Test Story", "Test Description");
        when(service.editStoryById(1, story, (UserDetails) authentication.getPrincipal()))
                .thenReturn(story);
        story.setAuthorUsername("simanta");
        ResponseEntity<?>entity = controller.editStoryById(1, story, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        assertEquals(entity.getBody(), story);
    }

    @Test
    @DisplayName("Should Not Edit Story because story id not found (Story Controller file)")
    void shouldNotEditStory() throws Exception {
        Story story = new Story(1, "Test Story", "Test Description");
        when(service.editStoryById(1, story, (UserDetails) authentication.getPrincipal()))
                .thenReturn(null);
        ResponseEntity<?>entity = controller.editStoryById(1, story, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    @Test
    @DisplayName("Should not Delete Story because story id not found (Story Controller file)")
    void shouldNotDeleteStory() {
        when(service.deleteStoryById(1,  (UserDetails) authentication.getPrincipal()))
                .thenReturn(Optional.empty());
        ResponseEntity<?>entity = controller.deleteStoryById(1, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    @Test
    @DisplayName("Should Delete Story with all valid information (Story Controller file)")
    void shouldDeleteStory() {
        when(service.deleteStoryById(1,  (UserDetails) authentication.getPrincipal()))
                .thenReturn(Optional.of("Story with id: 1 is deleted."));
        ResponseEntity<?>entity = controller.deleteStoryById(1, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}