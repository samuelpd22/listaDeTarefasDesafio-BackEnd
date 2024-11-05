package com.desafio.lTarefas.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class,})
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder messageBuilder = new StringBuilder("Erros de validação: ");

        ex.getConstraintViolations().forEach(violation -> {
            messageBuilder.append(violation.getMessage()).append(" ");
        });

        ErrorDTO erro = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), messageBuilder.toString().trim());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }
}
