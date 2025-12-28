package com.svalero.asociation.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private String title;
    private Map<String, String> error = new HashMap<>();
    public ErrorResponse(int code, String message, String title){
        this.code= code;
        this.message = message;
        this.title = title;
    }

    public void addError (String field, String message){
        this.error.put(field, message);
    }
}
