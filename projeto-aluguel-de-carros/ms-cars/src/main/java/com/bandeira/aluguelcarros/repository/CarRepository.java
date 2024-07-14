package com.bandeira.aluguelcarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandeira.aluguelcarros.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

	Car findByName(String nome);

}
