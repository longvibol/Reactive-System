package com.pisethjavaschool.userservice.user.exception;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("Resource not found");
        problem.setType(URI.create("https://api.pisethjavaschool.com/problems/not-found"));
        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(exception.getStatus(), exception.getMessage());
        problem.setTitle("Business validation failed");
        problem.setType(URI.create("https://api.pisethjavaschool.com/problems/business-validation"));
        problem.setProperty("errorCode", exception.getErrorCode());
        return problem;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ServerWebInputException.class})
    public ProblemDetail handleValidation(Exception exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
        problem.setTitle("Validation error");
        problem.setProperty("errors", List.of(exception.getMessage()));
        return problem;
    }
    
    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleBadRequest(
            BadRequestException ex,
            ServerWebExchange exchange
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                exchange.getRequest().getPath().value(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return Mono.just(ResponseEntity.badRequest().body(response));
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleResponseStatusException(
            ResponseStatusException ex,
            ServerWebExchange exchange
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                exchange.getRequest().getPath().value(),
                ex.getStatusCode().value(),
                ex.getStatusCode().toString(),
                ex.getReason()
        );

        return Mono.just(ResponseEntity
                .status(ex.getStatusCode())
                .body(response));
    }
    
    
}
