package com.akjostudios.acsp.auth.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public @NotNull ResponseEntity<ErrorResponse> handleError(@NotNull ResponseStatusException e, @NotNull ServerHttpRequest request) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        Integer.toString(e.getStatusCode().value()),
                        request.getURI().toString()
                ));
    }

    @RequiredArgsConstructor
    @Getter
    public static class ErrorResponse {
        private final String message;
        private final String code;
        private final String path;
    }
}