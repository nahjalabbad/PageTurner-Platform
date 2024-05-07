package com.example.pageturner.Advise;
import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException e){
        String message=e.getMessage();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationException(ConstraintViolationException e) {
        String msg =e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg=e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class )
    public ResponseEntity InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        String msg=e.getMessage();
        return ResponseEntity.status(200).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException e){
        String msg=e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity NullPointerException(NullPointerException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }
    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public ResponseEntity InvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }
    @ExceptionHandler(value = JpaSystemException.class)
    public ResponseEntity JpaSystemException(JpaSystemException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

    @ExceptionHandler(value = IncorrectResultSizeDataAccessException.class)
    public ResponseEntity IncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }
    @ExceptionHandler(value = ConversionFailedException.class)
    public ResponseEntity ConversionFailedException(ConversionFailedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }
    @ExceptionHandler(value = MissingPathVariableException.class)
    public ResponseEntity MissingPathVariableException(MissingPathVariableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity IllegalArgumentException(IllegalArgumentException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg));
    }

}
