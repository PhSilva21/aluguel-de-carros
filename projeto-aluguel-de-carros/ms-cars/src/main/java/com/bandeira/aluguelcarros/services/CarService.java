package com.bandeira.aluguelcarros.services;

import java.util.List;
import java.util.stream.Collectors;

import com.bandeira.aluguelcarros.dto.CarRequest;
import com.bandeira.aluguelcarros.dto.UpdateCarDTO;
import com.bandeira.aluguelcarros.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandeira.aluguelcarros.exceptions.CarNotFoundException;
import com.bandeira.aluguelcarros.model.Car;
import com.bandeira.aluguelcarros.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	public CarRequest createCar(CarRequest carRequest) {

		Car car =  new Car(
			carRequest.name(),
			carRequest.dailyPrice(),
			carRequest.available(),
			carRequest.category()
		);

		carRepository.save(car);

		return carRequest;
	}
	
	public List<Car> carsAvailable() {
		return carRepository.findAll().stream().filter(c -> c.getAvailable().equals(true))
				 .collect(Collectors.toList());

	 }

	public List<Car> findAll(){
		 return carRepository.findAll();
	}


	public Car findById(Long id) {
		return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
	}

	
	public void update(Long id, UpdateCarDTO updateCarDTO) {
		var car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);

		car.setName(updateCarDTO.name());
		car.setDailyPrice(updateCarDTO.dailyPrice());
		car.setAvailable(updateCarDTO.available());
		car.setAvailable(updateCarDTO.available());

		carRepository.save(car);
	}

	public List<Car> findByHatch(){
		return carsAvailable().stream().filter(c -> c.getCategory().equals(Category.HATCH))
				.collect(Collectors.toList());
	}


	public List<Car> findByMinivan(){
		return carsAvailable().stream().filter(c -> c.getCategory().equals(Category.MINIVAN))
				.collect(Collectors.toList());
	}


	public List<Car> findBySedan(){
		return carsAvailable().stream().filter(c -> c.getCategory().equals(Category.SEDAN))
				.collect(Collectors.toList());
	}


	public List<Car> findBySporty(){
		return carsAvailable().stream().filter(c -> c.getCategory().equals(Category.SPORTY))
				.collect(Collectors.toList());
	}

	public Car findByName(String name){
		return carRepository.findByName(name);
	}

	public void deleteById(Long id){

		var car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);

		carRepository.deleteById(id);
	}

}
