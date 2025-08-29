package com.example.bankcards.exception;

import com.example.bankcards.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), "USER_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRole(InvalidRoleException ex) {
        return buildResponse(ex.getMessage(), "INVALID_ROLE", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCardNotFound(CardNotFoundException ex) {
        return buildResponse(ex.getMessage(), "CARD_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCardAlreadyExists(CardAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), "CARD_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardOwnershipException.class)
    public ResponseEntity<ErrorResponse> handleCardOwnership(CardOwnershipException ex) {
        return buildResponse(ex.getMessage(), "CARD_OWNERSHIP_VIOLATION", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCardStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCardStatus(InvalidCardStatusException ex) {
        return buildResponse(ex.getMessage(), "INVALID_CARD_STATUS", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughFunds(NotEnoughFundsException ex) {
        return buildResponse(ex.getMessage(), "NOT_ENOUGH_FUNDS", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransaction(InvalidTransactionException ex) {
        return buildResponse(ex.getMessage(), "INVALID_TRANSACTION", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .sorted()
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation error");
        return buildResponse(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .sorted()
                .reduce((a,b) -> a + "; " + b)
                .orElse("Validation error");
        return buildResponse(msg, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBind(BindException ex) {
        String msg = ex.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .sorted()
                .reduce((a,b) -> a + "; " + b)
                .orElse("Bind error");
        return buildResponse(msg, "BIND_ERROR", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException ex) {
        return buildResponse("Request body is not readable", "REQUEST_NOT_READABLE", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildResponse("Parameter type mismatch: " + ex.getName(), "PARAM_TYPE_MISMATCH", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        return buildResponse("Missing parameter: " + ex.getParameterName(), "PARAM_MISSING", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuth(AuthenticationException ex) {
        return buildResponse("Unauthorized", "AUTH_UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse("Forbidden", "AUTH_FORBIDDEN", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("unhandled exception", ex);
        return buildResponse("Internal server error", "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, String code, HttpStatus status) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(message, code, LocalDateTime.now())
        );
    }
}

