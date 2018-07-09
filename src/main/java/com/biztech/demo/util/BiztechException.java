package com.biztech.demo.util;

import org.springframework.http.HttpStatus;

public class BiztechException extends RuntimeException {

    private String exceptionCode;
    private String exceptionMessage;
    private HttpStatus httpStatus;
    private Throwable cause;

    public BiztechException(){}

    public BiztechException(String exceptionCode, String exceptionMessage, HttpStatus httpStatus) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
    }

    public BiztechException(String exceptionCode, String exceptionMessage, HttpStatus httpStatus, Throwable cause) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
        this.cause = cause;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
