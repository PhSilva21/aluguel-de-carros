package com.bandeira.aluguelcarros.dto;

import com.bandeira.aluguelcarros.model.CarCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarRequest(

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "The name cannot be empty")
        String name,

        @NotNull(message = "dailyPrice cannot be null")
        BigDecimal dailyPrice,

        Boolean available,

        CarCategory category

) {
}
