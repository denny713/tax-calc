package com.tax.exception;

import com.tax.dto.ErrorDto;

import java.util.Arrays;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ErrorDto> errorDtos;

    public ValidationException(List<ErrorDto> objectErrorDtos) {
        this.errorDtos = objectErrorDtos;
    }

    public ValidationException(ErrorDto objectErrorDto) {
        this.errorDtos = Arrays.asList(objectErrorDto);
    }

    public List<ErrorDto> getErrorDtos() {
        return errorDtos;
    }

    public ValidationException(String message, List<ErrorDto> errorDtos) {
        super(message);
        this.errorDtos = errorDtos;
    }
}
