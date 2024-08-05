package com.bandeira.aluguelcarros.controllers;

import com.bandeira.aluguelcarros.dto.CarRequest;
import com.bandeira.aluguelcarros.dto.UpdateCarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bandeira.aluguelcarros.model.Car;
import com.bandeira.aluguelcarros.services.CarService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping("/create")
	public ResponseEntity<CarRequest> createCar(@RequestBody @Valid CarRequest carRequest) {
		var car = carService.createCar(carRequest);
		return ResponseEntity.ok().body(carRequest);
	}
	
	
	@GetMapping("/name")
    public ResponseEntity<Car> findByNome(@RequestParam @Param("name") String name){
		Car var = carService.findByName(name);
		return ResponseEntity.ok().body(var);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Void> update(@RequestBody UpdateCarDTO updateCarDTO){
		carService.update(updateCarDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<Car> findById(@RequestParam Long id){
		var car = carService.findById(id);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/listHatch")
	public ResponseEntity<List<Car>> findByHatch() {
		var carList = carService.findByHatch();
		return ResponseEntity.ok().body(carList);
	}


	@GetMapping("/listMinivans")
	public ResponseEntity<List<Car>> findByMinivan() {
		var carList = carService.findByMinivan();
		return ResponseEntity.ok().body(carList);
	}


	@GetMapping("/listSedans")
	public ResponseEntity<List<Car>> findBySedan() {
		var carList = carService.findBySedan();
		return ResponseEntity.ok().body(carList);
	}


	@GetMapping("/listSports")
	public ResponseEntity<List<Car>> findBySporty(){
		var carList = carService.findBySporty();
		return ResponseEntity.ok().body(carList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		carService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
