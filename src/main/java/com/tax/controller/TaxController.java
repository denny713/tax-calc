package com.tax.controller;

import com.tax.dto.ResponseDto;
import com.tax.service.TaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> doTaxCalculate(@RequestParam double annualIncome) throws Exception {
        return ResponseEntity.ok(taxService.calculateTax(annualIncome));
    }
}
