package com.blogging.demo.controller;

import com.blogging.demo.service.BlogPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostControllerTest {
    @Mock
    private BlogPostService blogPostService;

    @InjectMocks
    private BlogPostController blogPostController;

    @Test
    void shouldReturnResponseEntityWhenSaveIsSuccessful() throws JsonProcessingException {
        String content = "Content";
        doNothing().when(blogPostService).createNewBlogPost(content);

        ResponseEntity<String> response = blogPostController.create(content);

        Assertions.assertEquals("Created new blog post!", response.getBody());
        verify(blogPostService, times(1)).createNewBlogPost(content);
    }
}