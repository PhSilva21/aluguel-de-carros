package com.bandeira.reservation.controllers;

import com.bandeira.reservation.dtos.CouponRequest;
import com.bandeira.reservation.dtos.DadosEmail;
import com.bandeira.reservation.dtos.ReservationRequest;
import com.bandeira.reservation.model.DiscountCoupon;
import com.bandeira.reservation.services.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class LojaController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping("compra")
	public ResponseEntity<String> makeReservation(@RequestBody ReservationRequest reservaRequest) throws JsonProcessingException {
		return ResponseEntity.ok(reservationService.makeReservation(reservaRequest));
	}

	@PostMapping("sendEmail")
	public ResponseEntity<Void> sendingEmail(@RequestBody DadosEmail dados) {
		reservationService.sendingEmail(dados);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public DiscountCoupon create(@RequestBody CouponRequest couponRequest) {
		var coupon = reservationService.createCoupon(couponRequest);
		return coupon;
	}
}
