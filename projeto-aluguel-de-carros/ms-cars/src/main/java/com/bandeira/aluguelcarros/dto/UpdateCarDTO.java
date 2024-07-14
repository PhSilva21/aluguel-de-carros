package com.bandeira.aluguelcarros.dto;

import com.bandeira.aluguelcarros.model.Category;

public record UpdateCarDTO(

        String name,

        Double dailyPrice,

        Boolean available,

        Category category

) {
}
