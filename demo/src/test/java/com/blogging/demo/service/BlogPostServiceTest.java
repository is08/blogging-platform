package com.blogging.demo.service;

import com.blogging.demo.entities.BlogPost;
import com.blogging.demo.exception.NoBlogPostsExistException;
import com.blogging.demo.repository.BlogPostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogPostServiceTest {
    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @Test
    void shouldCreateANewBlogPostWhenContentIsGiven() throws JsonProcessingException {
        String content = "{\"title\":\"isconsumerismreal?\",\"content\":\"thisisthecontent\",\"email\":\"isha@abc.com\"}";

        blogPostService.createNewBlogPost(content);

        verify(blogPostRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnBlogPostWhenEmailIsGiven() {
        BlogPost blog = mock(BlogPost.class);
        String email = "email@xyz.com";
        when(blogPostRepository.findAllByEmail(email)).thenReturn(List.of(blog));

        List<BlogPost> blogByEmail = blogPostService.getByEmail(email);

        assertEquals(List.of(blog), blogByEmail);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsGivenAndBlogIsNotAvailable() {
        String email = "email@xyz.com";
        when(blogPostRepository.findAllByEmail(email)).thenReturn(List.of());

        assertThrows(NoBlogPostsExistException.class,
                () -> blogPostService.getByEmail(email),
                "No blog post exists!");
    }

    @Test
    void shouldReturnBlogPostWhenTitleIsGiven() {
        BlogPost blog = mock(BlogPost.class);
        String title = "title";
        when(blogPostRepository.findAllByTitle(title)).thenReturn(List.of(blog));

        List<BlogPost> blogByTitle = blogPostService.getByTitle(title);

        assertEquals(List.of(blog), blogByTitle);
    }

    @Test
    void shouldThrowExceptionWhenTitleIsGivenAndBlogIsNotAvailable() {
        String title = "title";
        when(blogPostRepository.findAllByTitle(title)).thenReturn(List.of());

        assertThrows(NoBlogPostsExistException.class,
                () -> blogPostService.getByTitle(title),
                "No blog post exists!");
    }

    @Test
    void shouldUpdateBlogIfBlogIsPresent() throws JsonProcessingException {
        BlogPost blogPost = mock(BlogPost.class);
        when(blogPostRepository.findById(1)).thenReturn(Optional.of(blogPost));
        String content = "{\"title\":\"updated title\",\"content\":\"this is the updated content\"}";

        blogPostService.updateBlog(1, content);

        verify(blogPostRepository, times(1)).save(blogPost);
    }

    @Test
    void shouldThrowExceptionIfBlogIsNotPresent() throws JsonProcessingException {
        when(blogPostRepository.findById(1)).thenReturn(Optional.empty());
        String content = "{\"title\":\"updated title\",\"content\":\"this is the updated content\"}";

        assertThrows(NoBlogPostsExistException.class,
                () -> blogPostService.updateBlog(1, content),
                "No blog post exists!");
    }

    @Test
    void shouldDeleteBlogPostIfPresent() {
        BlogPost blogPost = mock(BlogPost.class);
        when(blogPostRepository.findById(1)).thenReturn(Optional.of(blogPost));

        blogPostService.deleteById(1);

        verify(blogPostRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowExceptionIfBlogIsNotPresentWhileDeletion() {
        when(blogPostRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoBlogPostsExistException.class,
                () -> blogPostService.deleteById(1),
                "No blog post exists!");
    }
}
