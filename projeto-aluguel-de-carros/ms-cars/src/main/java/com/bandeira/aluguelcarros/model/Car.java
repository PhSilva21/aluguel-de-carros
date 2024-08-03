package com.bandeira.aluguelcarros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "cars")
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String name;


	private BigDecimal dailyPrice;


	private Boolean available;


	private Category category;

	public Car() {
		super();
	}
	

	public Car(String name, BigDecimal dailyPrice, Boolean available, Category category) {
		this.name = name;
		this.dailyPrice = dailyPrice;
		this.available = available;
		this.category = category;
	}

}
