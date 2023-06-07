package com.blogging.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public record BlogPostRequest(String title, String email, String content) {
}
