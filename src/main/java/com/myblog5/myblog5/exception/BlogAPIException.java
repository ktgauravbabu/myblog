package com.myblog5.myblog5.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends Throwable {
    public BlogAPIException(HttpStatus httpStatus, String invalidJwtSignature) {
    }
}
