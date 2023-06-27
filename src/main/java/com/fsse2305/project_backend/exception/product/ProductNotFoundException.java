package com.fsse2305.project_backend.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException {
}