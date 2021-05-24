package com.example.crudrestapi.service;

import com.example.crudrestapi.model.Story;
import com.example.crudrestapi.repository.StoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class StoryServiceTest {
    private final StoryRepository repository = mock(StoryRepository.class);
    private final UserDetails userDetails = mock(UserDetails.class);
    private final StoryService service = new StoryService(repository);

    @Test
    void shouldCreateStoryService() {

    }
}