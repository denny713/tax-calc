package com.tax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxResultDto {

    private double taxAmount;
    private String taxSlab;
    private List<String> taxClaimOptions;
}
