package com.example.order_system_spring_boot.helpers;


import com.example.order_system_spring_boot.helpers.ResponseHandler;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> errorsList =  new ArrayList<>();


        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorsList.add(error.getDefaultMessage())
        );


        errors.put("errors" , errorsList);

        return ResponseHandler.responseBuilder("Validation error", HttpStatus.BAD_REQUEST, errorsList , false);
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    // Handles SQL/DB errors
    @ExceptionHandler(SqlScriptException.class)
    public ResponseEntity<Object> handleSqlScriptException(SqlScriptException ex) {
        return ResponseHandler.responseBuilder(
                "Database error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                false
        );
    }

    // (Optional) Catch Spring’s DataAccessException too, if not wrapped
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        return ResponseHandler.responseBuilder(
                "Unexpected database error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMostSpecificCause().getMessage(),
                false
        );
    }



}