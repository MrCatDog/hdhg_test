package com.example.jokeschucktest.error;

public class ApiException extends Exception {
    public ApiException(String errorMassage, Throwable err) {
        super(errorMassage, err);
    }
}
