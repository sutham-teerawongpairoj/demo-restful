package com.biztech.demo.util;

import com.biztech.demo.constants.GlobalConstant;
import org.springframework.http.HttpStatus;

public class BiztechException extends RuntimeException {

    private String exceptionProject;
    private String exceptionCode;
    private String exceptionMessage;
    private HttpStatus httpStatus;
    private Exception exception;


    public BiztechException(){}


    public BiztechException(String exceptionProject, String exceptionCode, String exceptionMessage, Exception exception) {
        this.exceptionProject = exceptionProject;
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exception = exception;
    }

    public BiztechException(String exceptionProject, String exceptionCode, String exceptionMessage, Exception exception, String pName, String pValue) {
        this.exceptionProject = exceptionProject;
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage.replaceAll(GlobalConstant.PARMATER_NOT_FOUND_P_NAME, pName).replaceAll(GlobalConstant.PARMATER_NOT_FOUND_P_VALUE, pValue);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exception = exception;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExceptionProject() {
        return exceptionProject;
    }

    public void setExceptionProject(String exceptionProject) {
        this.exceptionProject = exceptionProject;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
