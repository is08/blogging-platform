package com.blogging.demo.exception;

public class NoBlogPostsExistException extends RuntimeException {
    public NoBlogPostsExistException(String message) {
        super(message);
    }
}
