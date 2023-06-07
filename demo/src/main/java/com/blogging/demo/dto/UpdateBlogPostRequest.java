package com.blogging.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public record UpdateBlogPostRequest (String title, String content){
}
