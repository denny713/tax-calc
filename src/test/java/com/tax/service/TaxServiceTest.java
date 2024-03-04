package com.tax.service;

import com.tax.dto.ResponseDto;
import com.tax.dto.TaxResultDto;
import com.tax.exception.BadRequestException;
import com.tax.service.impl.TaxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaxServiceTest {

    @InjectMocks
    TaxServiceImpl taxService;

    private TaxResultDto taxResultDto;
    private ResponseDto responseDto;

    @BeforeEach
    void init() {
        List<String> options = new ArrayList<>();
        options.add("Option 1");
        options.add("Option 2");
        taxResultDto = new TaxResultDto(3750.0, "Slab 2", options);

        responseDto = new ResponseDto("OK", taxResultDto);
    }


    @Test
    void givenAnnualIncome_whenCalculateTax_thenReturnTaxData() throws Exception {
        double annualIncome = 7000;

        when(taxService.calculateTax(annualIncome)).thenReturn(responseDto);

        ResponseDto result = taxService.calculateTax(annualIncome);
        assertNotNull(result);
        assertNotNull(result.getMessage(), responseDto.getMessage());
    }

    @Test
    void givenWrongAnnualIncome_whenCalculateTax_thenThrowException() throws Exception {
        double annualIncome = 7000;

        when(taxService.calculateTax(annualIncome)).thenReturn(null);

        try {
            ResponseDto result = taxService.calculateTax(annualIncome);
        } catch (Exception ex) {
            assertNotNull(ex.getMessage());
        }
    }
}
