package com.zipcook_server.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public abstract class zipcookException extends  RuntimeException{

    public final Map<String,String> validation=new HashMap<>();

    public zipcookException(String message) {
        super(message);
    }

    public zipcookException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName,String message){
        validation.put(fieldName, message);
    }
}
