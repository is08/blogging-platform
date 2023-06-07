package com.blogging.demo.service;

import com.blogging.demo.dto.BlogPostRequest;
import com.blogging.demo.dto.UpdateBlogPostRequest;
import com.blogging.demo.entities.BlogPost;
import com.blogging.demo.exception.NoBlogPostsExistException;
import com.blogging.demo.repository.BlogPostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    public void createNewBlogPost(String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogPostRequest blogPostRequest = objectMapper.readValue(request, BlogPostRequest.class);

        //TODO: add validation for user to exist
        BlogPost newBlogPost = new BlogPost(blogPostRequest.title(), LocalDateTime.now(),
                blogPostRequest.email(), blogPostRequest.content());

        blogPostRepository.save(newBlogPost);
    }

    public List<BlogPost> getByEmail(String email) {
        List<BlogPost> posts = blogPostRepository.findAllByEmail(email);

        if(CollectionUtils.isEmpty(posts)) {
            throw new NoBlogPostsExistException("No blog post exists!");
        }

        return posts;
    }

    public List<BlogPost> getByTitle(String title) {
        List<BlogPost> posts = blogPostRepository.findAllByTitle(title);

        if(CollectionUtils.isEmpty(posts)) {
            throw new NoBlogPostsExistException("No blog post exists!");
        }

        return posts;
    }

    public void updateBlog(Integer id, String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateBlogPostRequest updatedBlogPostRequest = objectMapper.readValue(content, UpdateBlogPostRequest.class);

        Optional<BlogPost> byId = blogPostRepository.findById(id);

        if(byId.isEmpty()) {
            throw new NoBlogPostsExistException("No blog post exists!");
        }

        BlogPost blogPost = byId.get();

        if(StringUtils.hasLength(updatedBlogPostRequest.content())) {
            blogPost.setContent(updatedBlogPostRequest.content());
        }
        if(StringUtils.hasLength(updatedBlogPostRequest.title())) {
            blogPost.setTitle(updatedBlogPostRequest.title());
        }

        blogPostRepository.save(blogPost);
    }

    public void deleteById(Integer id) {
        Optional<BlogPost> byId = blogPostRepository.findById(id);

        if(byId.isEmpty()) {
            throw new NoBlogPostsExistException("No blog post exists!");
        }

        blogPostRepository.deleteById(id);
    }
}
