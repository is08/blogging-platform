package com.blogging.demo.entities;

import com.blogging.demo.dto.BlogPostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "blog_post")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String email;
    private String content;

    public BlogPost(String title, LocalDateTime createdAt, String email, String content) {
        this.title = title;
        this.createdAt = createdAt;
        this.email = email;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogPost() {}
}
