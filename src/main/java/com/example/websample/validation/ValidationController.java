package com.example.websample.validation;

import com.example.websample.dto.ErrorResponse;
import org.apache.coyote.Request;
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
    public ValidationDto check(@RequestBody @Valid Request request){
        ValidationDto validationDto = (ValidationDto) request.getAttribute("validationDto");
        return validationDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("newHeader", "Some Value")
                .body(new ErrorResponse("INVALID_ACCESS",
                        "IllegalAccessException is occured."));
                // cf. ErrorResponse는 커스텀 에러 객체
    }
}