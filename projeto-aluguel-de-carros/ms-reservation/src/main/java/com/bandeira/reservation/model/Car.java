package com.bandeira.reservation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    private Long id;

    private String name;

    private Double dailyPrice;

    private Boolean available;

    private String category;
}
