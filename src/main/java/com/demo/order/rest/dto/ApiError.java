package com.demo.order.rest.dto;

public class ApiError {
    public String error;
    public String details;
    public ApiError(String error, String details) {
        this.error = error;
        this.details = details;
    }
}
