package com.bandeira.reservation.controllers;

import com.bandeira.reservation.dtos.CouponRequest;
import com.bandeira.reservation.dtos.DadosEmail;
import com.bandeira.reservation.dtos.ReservaRequest;
import com.bandeira.reservation.model.DiscountCoupon;
import com.bandeira.reservation.repositories.DiscountCouponRepository;
import com.bandeira.reservation.services.LojaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class LojaController {

	@Autowired
	private LojaService lojaService;
	
	 @PostMapping("compra")
	 public ResponseEntity<String> makeReservation(@RequestBody ReservaRequest reservaRequest) throws JsonProcessingException {
		 return ResponseEntity.ok(lojaService.makeReservation(reservaRequest));
	 }

	@PostMapping("sendEmail")
	public ResponseEntity<Void> sendingEmail(@RequestBody DadosEmail dados) {
		lojaService.sendingEmail(dados);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public DiscountCoupon create(@RequestBody CouponRequest couponRequest){
		 var coupon = lojaService.createCoupon(couponRequest);
		 return coupon;
	}

	@GetMapping("{coupon}")
	public Boolean discountCoupon(@PathVariable String coupon)		{
		return lojaService.discountCoupon(coupon);
		}

}
