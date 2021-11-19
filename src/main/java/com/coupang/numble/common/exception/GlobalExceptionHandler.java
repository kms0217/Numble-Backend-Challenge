package com.coupang.numble.common.exception;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity RuntimeExceptionHandler(RuntimeException e) {
        HashMap<String, String> m = new HashMap<>();
        m.put("errorMsg", e.getMessage());
        return ResponseEntity.status(400).body(m);
    }
}
