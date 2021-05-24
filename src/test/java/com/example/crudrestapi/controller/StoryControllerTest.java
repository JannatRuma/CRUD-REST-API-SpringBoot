package com.example.crudrestapi.controller;
import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.service.StoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

class StoryControllerTest {

    private final StoryService service = mock(StoryService.class);
    private final Authentication authentication = mock(Authentication.class);

    private final StoryController controller = new StoryController(service);
    private UserDetails userDetails;
    @BeforeEach
    void beforeEach() {
        userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "simanta";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
    @Test
    @DisplayName("Should Create Story with all valid information (Story Controller file)")
    void shouldCreateStory() {
        Story story = new Story(1, "Test Story", "Test Description");
        when(service.createStory(story))
                .thenReturn(story);
        when(((UserDetails)authentication.getPrincipal()))
                .thenReturn(userDetails);
        ResponseEntity<?> entity = controller.createStory(story, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);
        verify(service).createStory(story);
    }

    @Test
    @DisplayName("Should Not Create Story for including author username (Story Controller file)")
    void shouldNotCreateStoryForAuthorUsername() {
        Story story = new Story(1, "Test Story", "Test Description", "simanta");
        when(service.createStory(story))
                .thenReturn(story);
        when(((UserDetails)authentication.getPrincipal()))
                .thenReturn(userDetails);
        story.setAuthorUsername("simanta");
        ResponseEntity<?> entity = controller.createStory(story, authentication);
        assertEquals(entity.getBody(), "You should not include the author username in the request body!! Login first");
        assertEquals(entity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Should Edit Story with all valid information (Story Controller file)")
    void shouldEditStory() throws Exception {
        Story story = new Story(1, "Test Story", "Test Description", "simanta");
        when(service.getStoryById(1))
                .thenReturn(story);
        when(((UserDetails)authentication.getPrincipal()))
                .thenReturn(userDetails);
        when(service.editStoryById(story, story))
                .thenReturn(story);
        story.setAuthorUsername("simanta");
        ResponseEntity<?>entity = controller.editStoryById(1, story, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        assertEquals(entity.getBody(), story);
    }


    @Test
    @DisplayName("Should Delete Story with all valid information (Story Controller file)")
    void shouldDeleteStory() {
        Story story = new Story(1, "Test Story", "Test Description", "simanta");
        when(service.getStoryById(1))
                .thenReturn(story);
        when(((UserDetails)authentication.getPrincipal()))
                .thenReturn(userDetails);

        when(service.deleteStoryById(1))
                .thenReturn("Story with id: 1 is successfully deleted!");
        ResponseEntity<?>entity = controller.deleteStoryById(1, authentication);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}