package com.bandeira.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Car {

    private Long id;

    private String name;

    private BigDecimal dailyPrice;

    private Boolean available;

    private String category;
}
