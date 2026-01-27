package com.svalero.asociation.exception;

import com.svalero.asociation.controller.SocioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// mensaje de error customizado implantado en los Elsethrow in capa service va 'subiendo' por *NotFoundException,
// los cuales son hijos de ResourceNotFound y este de RunTimeExcepction, el handler lo maneja haciendo un objeto ErrorResponse
//Exception clase reemplazado por RunTime , evitar tener que hacer un throw excepcion en los metodos que necesitas manejar

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(SocioController.class);

    @ExceptionHandler(ResourceNotFoundException.class)//si el .class deja de ser el padre, se produce error 500
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        logger.error("Resource not found", ex);
        ErrorResponse error = ErrorResponse.generalError(404, ex.getMessage(), "Resource Not Found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        ErrorResponse error = ErrorResponse.generalError(400, "Validation failed for one or more fields", "Bad Request");
        logger.error("Not valid", ex);
        ex.getBindingResult().getAllErrors().forEach(f -> {
            if (f instanceof FieldError fieldError) {
                error.addError(fieldError.getField(), f.getDefaultMessage());
            } else {
                error.addError(f.getObjectName(), f.getDefaultMessage());
            }
        });

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestManual(BusinessRuleException ex) {
        ErrorResponse error =  ErrorResponse.generalError(409, ex.getMessage(), "Ya hay un usuario con esas credenciales");
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGlobalError(Exception ex) {
        ErrorResponse error =  ErrorResponse.generalError(500, "An unexpected error occurred", "Internal Server Error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
