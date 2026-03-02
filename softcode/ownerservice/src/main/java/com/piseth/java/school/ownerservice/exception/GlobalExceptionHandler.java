package com.piseth.java.school.ownerservice.exception;

import java.net.URI;
import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(BadRequestException.class)
    public Mono<ProblemDetail> handleBadRequest(BadRequestException ex, ServerWebExchange exchange) {
        log.warn("Bad request. path={}", exchange.getRequest().getPath(), ex);
        return Mono.just(problem(exchange, HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage(), "/errors/bad-request"));
    }
    
    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ProblemDetail> handleDuplicateKey(DuplicateKeyException ex, ServerWebExchange exchange) {
        log.warn("Duplicate key error. path={}", exchange.getRequest().getPath(), ex);

        String detail = "Resource already exists.";

        // Customize message for Email duplicate
        if (ex.getMessage() != null && ex.getMessage().contains("ux_owners_email")) {
            detail = "Email already exists.";
        }
        
        // Customize message for phone duplicate
        if (ex.getMessage() != null && ex.getMessage().contains("ux_owners_phone")) {
            detail = "Phone already exists.";
        }

        return Mono.just(
            problem(
                exchange,
                HttpStatus.BAD_REQUEST,
                "Duplicate resource",
                detail,
                "/errors/bad-request"
            )
        );
    } 
    

/*
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ProblemDetail> handleValidation(WebExchangeBindException ex, ServerWebExchange exchange) {
        log.warn("Validation error. path={}", exchange.getRequest().getPath(), ex);

        String message = "Validation failed.";
        if (!ex.getAllErrors().isEmpty() && ex.getAllErrors().get(0).getDefaultMessage() != null) {
            message = ex.getAllErrors().get(0).getDefaultMessage();
        }

        return Mono.just(problem(exchange, HttpStatus.BAD_REQUEST, "Validation error", message, "/errors/validation-error"));
    }
    
    

    @ExceptionHandler(Exception.class)
    public Mono<ProblemDetail> handleGeneric(Exception ex, ServerWebExchange exchange) {
        // IMPORTANT: log the stacktrace
        log.error("Unhandled exception. path={}", exchange.getRequest().getPath(), ex);

        return Mono.just(problem(exchange, HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal error", "Unexpected error occurred.", "/errors/internal-error"));
    }
*/
    private ProblemDetail problem(ServerWebExchange exchange, HttpStatus status, String title, String detail, String typePath) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        
        //typePath: we redirect to the link detail solution in our organization 
        pd.setType(URI.create(typePath));

        // instance = request path (nice for debugging)
        pd.setInstance(URI.create(exchange.getRequest().getPath().value()));

        // add timestamp + traceId our own custom
        pd.setProperty("timestamp", Instant.now().toString());
        pd.setProperty("traceId", exchange.getRequest().getId());

        return pd;
    }
}