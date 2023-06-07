package com.blogging.demo.repository;

import com.blogging.demo.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    List<BlogPost> findAllByEmail(String email);

    List<BlogPost> findAllByTitle(String title);
}