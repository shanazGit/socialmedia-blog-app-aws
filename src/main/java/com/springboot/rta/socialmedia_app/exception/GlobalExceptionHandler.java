package com.springboot.rta.socialmedia_app.exception;

import com.springboot.rta.socialmedia_app.dto.ErrorDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
       return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails>handleBlogApiException(
            BlogApiException blogApiException, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), blogApiException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, blogApiException.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails>handleException(
            Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}