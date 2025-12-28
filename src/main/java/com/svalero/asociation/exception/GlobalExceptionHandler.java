package com.svalero.asociation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// mensaje de error customizado es dado como input String a los metodos NotFound mediante Elsethrow in capa service,
// los cuales son hijos de ResourceNotFound, el handler lo maneja haciendo un objeto ErrorResponse
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- HANDLE 404 (Not Found) ---
    @ExceptionHandler(ResourceNotFoundException.class)//si el .class deja de ser el padre, se produce error 500
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        ErrorResponse error = ErrorResponse.generalError(404, ex.getMessage(), "Resource Not Found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // --- HANDLE 400 (Bad Request / Validation) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        ErrorResponse error = ErrorResponse.generalError(400, "Validation failed for one or more fields", "Bad Request");
        ex.getBindingResult().getFieldErrors().forEach(f ->
                error.addError(f.getField(), f.getDefaultMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestManual(BusinessRuleException ex) {
        // Aquí el HashMap se queda vacío porque es un error general, no de un campo específico
        ErrorResponse error =  ErrorResponse.generalError(400, ex.getMessage(), "Ya hay un usuario con esas credenciales");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // --- HANDLE 500 (Internal Server Error) ---
    // This catches everything else that you didn't specifically plan for
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGlobalError(Exception ex) {
        ErrorResponse error =  ErrorResponse.generalError(500, "An unexpected error occurred", "Internal Server Error");
        // Log the actual error for debugging
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
