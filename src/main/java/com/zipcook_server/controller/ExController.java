package com.zipcook_server.controller;

import com.zipcook_server.data.response.ErrorResponse;
import com.zipcook_server.exception.zipcookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse body= ErrorResponse.builder()
                .code(String.valueOf("400"))
                .message("잘못된 요청입니다")
                .build();

        for (FieldError fieldError: e.getFieldErrors()) {
            body.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return body;
    }

    @ResponseBody
    @ExceptionHandler(zipcookException.class)
    public ResponseEntity<ErrorResponse> zipcookException(zipcookException e){
        int statusCode=e.getStatusCode();

        ErrorResponse body= ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response=ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }
}
