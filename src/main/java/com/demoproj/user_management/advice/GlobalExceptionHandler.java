package com.demoproj.user_management.advice;

import com.demoproj.user_management.exceptions.BusinessRuleException;
import com.demoproj.user_management.exceptions.ResourceNotFoundException;
import com.demoproj.user_management.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handelResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("success",  false);
        map.put("status", HttpStatus.NOT_FOUND);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handelBusinessRuleException(BusinessRuleException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("success",  false);
        map.put("status", HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handelMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, Object> map = new HashMap<>();
        StringBuilder errMsg = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach((err) -> {
           errMsg.append(err.getField())
                   .append(" : ")
                   .append(err.getDefaultMessage())
                   .append(",");
        });

        map.put("message", errMsg.toString());
        map.put("success",  false);
        map.put("status", HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handelUnauthorizedException(UnauthorizedException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("success",  false);
        map.put("status", HttpStatus.UNAUTHORIZED);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handelRuntimeException(RuntimeException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("success",  false);
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(map);
    }
}
