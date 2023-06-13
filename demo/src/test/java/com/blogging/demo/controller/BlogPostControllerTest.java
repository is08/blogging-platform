package com.blogging.demo.controller;

import com.blogging.demo.entities.BlogPost;
import com.blogging.demo.exception.NoBlogPostsExistException;
import com.blogging.demo.service.BlogPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @Test
    void shouldReturnResponseEntityWhenSaveIsUnsuccessful() throws JsonProcessingException {
        String content = "Content";
        JsonProcessingException exception = mock(JsonProcessingException.class);
        doThrow(exception).when(blogPostService).createNewBlogPost(content);

        ResponseEntity<String> response = blogPostController.create(content);

        Assertions.assertEquals("Exception! Check logs", response.getBody());
        verify(blogPostService, times(1)).createNewBlogPost(content);
    }

    @Test
    void shouldReturnResponseEntityWhenBlogIsAvailable() throws JsonProcessingException {
        String email = "abc@xyz.com";
        List<BlogPost> blogPosts = mock(List.class);
        when(blogPostService.getByEmail(email)).thenReturn(blogPosts);

        ResponseEntity response = blogPostController.getByEmail(email);

        Assertions.assertEquals(blogPosts, response.getBody());
        verify(blogPostService, times(1)).getByEmail(email);
    }

    @Test
    void shouldReturnResponseEntityWhenBlogIsNotAvailable() throws JsonProcessingException {
        String email = "abc@xyz.com";
        List<BlogPost> blogPosts = mock(List.class);
        NoBlogPostsExistException exception = mock(NoBlogPostsExistException.class);
        when(exception.getMessage()).thenReturn("No blog post exists!");
        doThrow(exception).when(blogPostService).getByEmail(email);

        ResponseEntity response = blogPostController.getByEmail(email);

        Assertions.assertEquals("No blog post exists!", response.getBody());
        verify(blogPostService, times(1)).getByEmail(email);
    }

    @Test
    void shouldReturnResponseEntityWhenBlogIsAvailableByTitle() throws JsonProcessingException {
        String title = "title";
        List<BlogPost> blogPosts = mock(List.class);
        when(blogPostService.getByTitle(title)).thenReturn(blogPosts);

        ResponseEntity response = blogPostController.getByTitle(title);

        Assertions.assertEquals(blogPosts, response.getBody());
        verify(blogPostService, times(1)).getByTitle(title);
    }

    @Test
    void shouldReturnResponseEntityWhenBlogIsNotAvailableByTitle() throws JsonProcessingException {
        String title = "title";
        List<BlogPost> blogPosts = mock(List.class);
        NoBlogPostsExistException exception = mock(NoBlogPostsExistException.class);
        when(exception.getMessage()).thenReturn("No blog post exists!");
        doThrow(exception).when(blogPostService).getByTitle(title);

        ResponseEntity response = blogPostController.getByTitle(title);

        Assertions.assertEquals("No blog post exists!", response.getBody());
        verify(blogPostService, times(1)).getByTitle(title);
    }
}