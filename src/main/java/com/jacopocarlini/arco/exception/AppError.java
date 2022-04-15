package com.jacopocarlini.arco.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum AppError {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Something was wrong", 0L),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request", "Invalid input", 1L),
    UNATHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized", "Invalid credential", 2L),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found", "email '%s' doesn't exist", 3L),
    UNKNOWN(null, null, null, null);

    public final HttpStatus httpStatus;
    public final String title;
    public final String details;
    public final Long code;


    AppError(HttpStatus httpStatus, String title, String details, Long code) {
        this.httpStatus = httpStatus;
        this.title = title;
        this.details = details;
        this.code = code;
    }
}


