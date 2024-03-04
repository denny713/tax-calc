package com.tax.service;

import com.tax.dto.ResponseDto;

public interface TaxService {

    ResponseDto calculateTax(double annualIncome) throws Exception;
}
