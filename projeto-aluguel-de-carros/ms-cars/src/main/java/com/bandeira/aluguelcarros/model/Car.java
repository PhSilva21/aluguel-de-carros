package com.bandeira.aluguelcarros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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


	private CarCategory category;

	public Car() {
		super();
	}
	

	public Car(String name, BigDecimal dailyPrice, Boolean available, CarCategory category) {
		this.name = name;
		this.dailyPrice = dailyPrice;
		this.available = available;
		this.category = category;
	}

}
