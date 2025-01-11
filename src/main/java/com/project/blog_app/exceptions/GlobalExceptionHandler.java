package com.project.blog_app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.blog_app.payloads.ApiResopnse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResopnse> resourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResopnse apiResopnse = new ApiResopnse(message, false); 
        return new ResponseEntity<ApiResopnse>(apiResopnse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String , String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((errors)->{
            String fieldName = ((FieldError)errors).getField();
            String message = errors.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResopnse> handleApiException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResopnse apiResopnse = new ApiResopnse(message, false); 
        return new ResponseEntity<ApiResopnse>(apiResopnse,HttpStatus.BAD_REQUEST);
    }
}
