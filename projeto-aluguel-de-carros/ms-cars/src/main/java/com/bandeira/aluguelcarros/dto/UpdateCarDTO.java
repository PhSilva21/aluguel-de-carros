package com.bandeira.aluguelcarros.dto;

import com.bandeira.aluguelcarros.model.Category;

import java.math.BigDecimal;

public record UpdateCarDTO(

        String name,

        BigDecimal dailyPrice,

        Boolean available,

        Category category

) {
}
