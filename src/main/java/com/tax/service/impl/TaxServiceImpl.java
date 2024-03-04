package com.tax.service.impl;

import com.tax.dto.ResponseDto;
import com.tax.dto.TaxResultDto;
import com.tax.exception.BadRequestException;
import com.tax.service.TaxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {

    private static final double[] SLABS = {5000, 20000, 35000, 50000, 70000, 100000, 400000, 600000, 2000000};
    private static final double[] RATES = {0, 1, 3, 6, 11, 19, 25, 26, 28, 30};
    private static final double[] DEDUCTIONS = {0, 0, 150, 450, 900, 2200, 5700, 75000, 52000, 392000};

    @Override
    public ResponseDto calculateTax(double annualIncome) throws Exception {
        try {
            double taxAmount = 0;
            String taxSlab = "";
            List<String> taxClaimOptions = new ArrayList<>();

            for (int i = 0; i < SLABS.length; i++) {
                if (annualIncome <= SLABS[i]) {
                    taxAmount = (annualIncome - SLABS[i - 1]) * RATES[i] / 100 + DEDUCTIONS[i];
                    taxSlab = "Slab " + (i + 1);
                    break;
                }
            }

            taxClaimOptions.add("Option 1");
            taxClaimOptions.add("Option 2");

            return new ResponseDto("OK", new TaxResultDto(taxAmount, taxSlab, taxClaimOptions));
        } catch (BadRequestException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
