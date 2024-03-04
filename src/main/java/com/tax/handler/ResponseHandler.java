package com.tax.handler;

import com.tax.dto.ErrorDto;
import com.tax.dto.ErrorResultDto;
import com.tax.exception.BadRequestException;
import com.tax.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ResponseHandler {

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String ERR_VALIDATION = "ERR_VALIDATION";
    private static final String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResultDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<ErrorDto> errorMessages = result.getFieldErrors()
                .stream()
                .map(objectError -> {
                    String message =
                            StringUtils.isEmpty(objectError.getDefaultMessage()) ? "" : objectError.getDefaultMessage();
                    ErrorDto dto = new ErrorDto();
                    dto.setObject(objectError.getField());
                    dto.setMessage(message);
                    dto.setValue(objectError.getRejectedValue());
                    return dto;
                }).collect(Collectors.toList());

        ErrorResultDto response = new ErrorResultDto();
        response.setCode(ERR_VALIDATION);
        response.setMessage("Invalid Request");
        response.setResult(errorMessages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResultDto> handleValidationCustomExceptions(ValidationException ex) {
        ErrorResultDto response = new ErrorResultDto();
        response.setCode(ERR_VALIDATION);
        response.setMessage(StringUtils.isEmpty(ex.getMessage()) ? "Invalid Request" : ex.getMessage());
        response.setResult(ex.getErrorDtos());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResultDto> handleAllUncaughtException(Exception exception, WebRequest request) {
        exception.printStackTrace();
        log.error(exception.getMessage());
        ErrorResultDto error = new ErrorResultDto();
        error.setCode(INTERNAL_SERVER_ERROR);
        error.setMessage("Oops something went wrong, please contact your administrator");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResultDto> handleBadRequestException(Exception ex) {
        log.error(ex.getMessage());
        ErrorResultDto error = new ErrorResultDto();
        error.setCode(BAD_REQUEST);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResultDto> handleInvalidEnumValue(Exception ex) {
        log.error(ex.getMessage());
        ErrorResultDto response = new ErrorResultDto();
        response.setCode(ERR_VALIDATION);
        response.setMessage("Invalid Request");
        response.setResult(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
