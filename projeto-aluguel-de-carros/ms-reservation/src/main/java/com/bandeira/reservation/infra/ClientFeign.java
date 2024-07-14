package com.bandeira.reservation.infra;

import com.bandeira.reservation.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ms-users", path = "/users")
public interface ClientFeign {

	@GetMapping(params = "email")
	Client findByEmail(@RequestParam("email")String email);



}
