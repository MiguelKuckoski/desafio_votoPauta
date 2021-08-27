package com.zallpy.Desafio_unicred.exception;

import com.zallpy.Desafio_unicred.exception.holder.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleException(CustomException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", e.getHttpStatus());
        body.put("error", e.getMessage());

        return new ResponseEntity<>(body, e.getHttpStatus());
    }
}
