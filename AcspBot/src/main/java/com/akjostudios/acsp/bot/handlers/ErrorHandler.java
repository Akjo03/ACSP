package com.akjostudios.acsp.bot.handlers;

import com.akjostudios.acsp.bot.model.ExceptionResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleException(@NotNull ResponseStatusException e, @NotNull ServerHttpRequest request) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ExceptionResponse(
                        e.getLocalizedMessage(),
                        Integer.toString(e.getStatusCode().value()),
                        request.getURI().toString()
                ));
    }
}