package com.tax.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResultDto {

    private String code;
    private String message;
    private Object result;
}
