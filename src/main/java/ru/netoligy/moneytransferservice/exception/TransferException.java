package ru.netoligy.moneytransferservice.exception;

public class TransferException extends RuntimeException{
    public TransferException(String message) {
        super(message);
    }
}
