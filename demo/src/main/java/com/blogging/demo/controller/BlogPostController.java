package com.blogging.demo.controller;

import com.blogging.demo.exception.NoBlogPostsExistException;
import com.blogging.demo.service.BlogPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/blog")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    private static Logger logger = LoggerFactory.getLogger(BlogPostController.class);

    @PostMapping
    ResponseEntity<String> create(@RequestBody String content) {
        try {
            blogPostService.createNewBlogPost(content);
        } catch (JsonProcessingException e) {
            logger.error("Exception occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Exception! Check logs");
        }
        return ResponseEntity.status(200).body("Created new blog post!");
    }

    @GetMapping("/email")
    ResponseEntity<? extends Object> getByEmail(@RequestParam String email) throws JsonProcessingException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(200).body(blogPostService.getByEmail(email));
        } catch (NoBlogPostsExistException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/title")
    ResponseEntity<? extends Object> getByTitle(@RequestParam String title) {
        try{
            return ResponseEntity.status(200).body(blogPostService.getByTitle(title));
        } catch (NoBlogPostsExistException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping
    ResponseEntity<String> update(@RequestParam Integer id, @RequestBody String content) throws JsonProcessingException {
        try {
            blogPostService.updateBlog(id, content);
            return ResponseEntity.status(200).body("Updated!");
        } catch (NoBlogPostsExistException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping
    ResponseEntity<String> delete(@RequestParam Integer id) {
        try {
            blogPostService.deleteById(id);
            return ResponseEntity.status(200).body("Deleted!");
        }
        catch (NoBlogPostsExistException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
