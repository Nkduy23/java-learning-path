package com.ai360.ecommerce.exception;

import com.ai360.ecommerce.dto.Dto.ErrorResponse;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * GLOBAL EXCEPTION HANDLER
 * ==========================
 * @RestControllerAdvice: bat exception tu MO controller, xu ly tap trung.
 * Thay vi moi controller tu try/catch, ta xu ly 1 cho duy nhat.
 *
 * @ExceptionHandler(XxxException.class): method nay xu ly loai exception do
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bat loi validation (@Valid fail)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        // Gom tat ca loi validation thanh 1 chuoi
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, message));
    }

    // Sat loi dang nhap sai
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, "Email hoac mat khau khong dung!"));
    }

    // Bat loi not found (RuntimeException chung)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        int status = ex.getMessage().contains("khong ton tai") ? 404 : 400;
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status, ex.getMessage()));
    }

    // Bat tat ca loi con lai
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "Loi he thong: " + ex.getMessage()));
    }
}
