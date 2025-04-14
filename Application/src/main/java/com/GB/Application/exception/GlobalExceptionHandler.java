package com.GB.Application.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TrackerException.class)
    public ResponseEntity<Object> handleTrackerExceptions(
            TrackerException ex, WebRequest request) {
        return buildErrorResponse(ex, ex.getErrorCode());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(
            ExpiredJwtException ex, WebRequest request) {
        return buildErrorResponse(ex, "TOKEN_EXPIRED");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
            Exception ex, WebRequest request) {
        return buildErrorResponse(ex, "INTERNAL_SERVER_ERROR");
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception, String errorCode) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", errorCode);
        body.put("message", exception.getMessage());

        HttpStatus status = determineHttpStatus(exception);
        return new ResponseEntity<>(body, status);
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        if (ex instanceof DeviceNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof ImeiExistsException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof ValidationException
                || ex instanceof ImeiNotFoundException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof LocationServiceException) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (ex instanceof ExpiredJwtException) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
