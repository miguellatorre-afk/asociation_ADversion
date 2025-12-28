package com.svalero.asociation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- HANDLE 404 (Not Found) ---
    // You can add multiple custom exceptions here separated by commas
    @ExceptionHandler({SocioNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(SocioNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage(), "Resource Not Found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // --- HANDLE 400 (Bad Request / Validation) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(400, "Validation failed for one or more fields", "Bad Request");
        // This is where your HashMap shines!
        ex.getBindingResult().getFieldErrors().forEach(f ->
                error.addError(f.getField(), f.getDefaultMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestManual(BusinessRuleException ex) {
        // Aquí el HashMap se queda vacío porque es un error general, no de un campo específico
        ErrorResponse error = new ErrorResponse(400, ex.getMessage(), "Ya hay un usuario con esas credenciales");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // --- HANDLE 500 (Internal Server Error) ---
    // This catches everything else that you didn't specifically plan for
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalError(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, "An unexpected error occurred", "Internal Server Error");
        // Log the actual error for debugging
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
