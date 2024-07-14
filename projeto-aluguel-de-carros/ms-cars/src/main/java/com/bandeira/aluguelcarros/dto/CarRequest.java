package com.bandeira.aluguelcarros.dto;

import com.bandeira.aluguelcarros.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequest(

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "The name cannot be empty")
        String name,

        @NotNull(message = "dailyPrice cannot be null")
        Double dailyPrice,

        Boolean available,

        Category category

) {
}
