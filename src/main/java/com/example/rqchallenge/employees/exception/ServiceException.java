package com.example.rqchallenge.employees.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    private CustomError customError;
    public ServiceException(final CustomError customError) {
        super();
        this.customError = customError;
    }
}
