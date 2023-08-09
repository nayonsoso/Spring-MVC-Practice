package com.example.websample.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ValidationController {
    @PostMapping("/valid")
    public ValidationDto check(@RequestBody @Valid ValidationDto validationDto){
        return validationDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}