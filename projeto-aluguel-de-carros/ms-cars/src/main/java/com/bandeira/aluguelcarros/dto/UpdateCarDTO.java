package com.bandeira.aluguelcarros.dto;

import com.bandeira.aluguelcarros.model.CarCategory;

import java.math.BigDecimal;

public record UpdateCarDTO(

        Long id,

        String name,

        BigDecimal dailyPrice,

        Boolean available,

        CarCategory category

) {
}
