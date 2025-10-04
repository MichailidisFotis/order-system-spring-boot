package com.example.order_system_spring_boot.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObject , Boolean success) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", message);
        response.put("status", httpStatus);

        if(success)
            response.put("data", responseObject);
        else
            response.put("errors" , responseObject);

        response.put("success" , success);

        return new ResponseEntity<>(response, httpStatus);


    }


}
