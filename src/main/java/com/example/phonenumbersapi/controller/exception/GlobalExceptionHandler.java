package com.example.phonenumbersapi.controller.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(annotations = MyExceptionHandler.class)
public final class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> badRequestException(final EntityNotFoundException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("You try to use object that not exist");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> badRequestException(final MethodArgumentTypeMismatchException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription(
                "The request is incorrect, please refer to the documentation to send the request correctly");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> badRequestException(final HttpMessageNotReadableException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response
                .setDescription("The request body was passed uncorrected, please refer to the documentation");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleMissingServletRequestParameterException(final MissingServletRequestParameterException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("One or more required request parameters are missing");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("The HTTP method specified in the request is not supported for this resource");

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleMissingPathVariableException(final MissingPathVariableException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("One or more required path variables are missing");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleIllegalArgumentException(final IllegalArgumentException ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("Bad args? Read the documentation");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleValidationExceptions(final Exception ex) {
        Response response = new Response();
        response.setMessage(ex.getLocalizedMessage());
        response.setDescription("Unknown error, read the logs to find out its cause");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
