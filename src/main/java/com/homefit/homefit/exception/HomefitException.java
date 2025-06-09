package com.homefit.homefit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HomefitException extends RuntimeException {
    private HttpStatus httpStatus;
    private String title;
    private String detail;

    public HomefitException(HttpStatus httpStatus, String title) {
        this.httpStatus = httpStatus;
        this.title = title;
    }

    public HomefitException(HttpStatus httpStatus, String title, String detail) {
        this.httpStatus = httpStatus;
        this.title = title;
        this.detail = detail;
    }
}
