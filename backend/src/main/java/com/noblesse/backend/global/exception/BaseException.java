package com.noblesse.backend.global.exception;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
