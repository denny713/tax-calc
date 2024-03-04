package com.tax.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tax.dto.ResponseDto;
import com.tax.dto.TaxResultDto;
import com.tax.service.impl.TaxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TaxController.class)
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
        double annualIncome = 6000;

        when(taxService.calculateTax(annualIncome)).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/tax?" + annualIncome)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        JsonObject jsonObject = JsonParser.parseString(result.getResponse().getContentAsString()).getAsJsonObject();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(responseDto.getMessage(), jsonObject.get("message").getAsString());
    }
}
