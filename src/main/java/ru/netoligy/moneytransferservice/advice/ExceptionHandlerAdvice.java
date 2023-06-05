package ru.netoligy.moneytransferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netoligy.moneytransferservice.exception.InputDataException;
import ru.netoligy.moneytransferservice.exception.TransferException;
import ru.netoligy.moneytransferservice.model.response.ErrorBody;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private final AtomicInteger id = new AtomicInteger(0);

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<ErrorBody> InputDataExceptionHandler(InputDataException e) {
        return new ResponseEntity<>(new ErrorBody(e.getMessage(), id.getAndIncrement()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ErrorBody> TransferExceptionHandler(TransferException e) {
        return new ResponseEntity<>(new ErrorBody(e.getMessage(), id.getAndIncrement()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
