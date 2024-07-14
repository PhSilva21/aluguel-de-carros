package com.bandeira.reservation.infra;

import com.bandeira.reservation.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ms-users", path = "/cars")
public interface CarroFeign {


	@GetMapping(params = "name")
	Car findByCar(@RequestParam("name") String name);

	@PostMapping(params = "id")
	void update(@RequestParam Long id, Car car);
}