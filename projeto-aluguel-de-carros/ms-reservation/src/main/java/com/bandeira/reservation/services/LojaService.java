package com.bandeira.reservation.services;

import com.bandeira.reservation.dtos.CouponRequest;
import com.bandeira.reservation.dtos.DadosEmail;
import com.bandeira.reservation.dtos.ReservaRequest;
import com.bandeira.reservation.exceptions.CarroNotFoundException;
import com.bandeira.reservation.exceptions.ClientNotFoundException;
import com.bandeira.reservation.exceptions.CouponNotFoundException;
import com.bandeira.reservation.exceptions.SendingEmailException;
import com.bandeira.reservation.infra.CarroFeign;
import com.bandeira.reservation.infra.ClientFeign;
import com.bandeira.reservation.infra.ReservationPublisher;
import com.bandeira.reservation.model.*;
import com.bandeira.reservation.repositories.DiscountCouponRepository;
import com.bandeira.reservation.repositories.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LojaService {


	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private DiscountCouponRepository discountCouponRepository;

	@Autowired
	private ClientFeign clientFeign;
	
	@Autowired
	private CarroFeign carroFeign;

	@Autowired
	EmailService emailService;

	private final ReservationPublisher reservationPublisher;

    public String makeReservation(ReservaRequest reservaRequest) throws JsonProcessingException {
		Client client = clientFeign.findByEmail(reservaRequest.email());

		if(client == null){
			throw new ClientNotFoundException();
		}

		Car car = carroFeign.findByCar(reservaRequest.carro());

		if(car == null){
			throw new CarroNotFoundException();
		}
		var days = ChronoUnit.DAYS.between(reservaRequest.retirada()
				, reservaRequest.devolucao());

		Reservation reservation = new Reservation(
				client.getEmail(),
				car.getName(),
				reservaRequest.retirada(),
				reservaRequest.devolucao(),
				StatusReservation.AWAITING_PAYMENT,
				days * car.getDailyPrice(),
				LocalDateTime.now());

		car.setAvailable(false);

		carroFeign.update(car.getId(), car);

		var coupon = discountCoupon(reservaRequest.couponName());

		if(coupon != null){
			var newValue = reservation.getValue() * coupon.getDiscountPercentage();
			reservation.setValue(newValue);
		}

		var email = emailService.emailPurchase(car.getName() + reservation.getWithdrawal()
				+ reservation.getDevolution());

		reservationPublisher.sendingEmail(email);

		return "sucsess";
	}

	public DiscountCoupon discountCoupon(String couponName){
		var coupon = discountCouponRepository.findByCouponName(couponName);

		if(coupon == null){
			throw new CouponNotFoundException();
		}
        return coupon ;
    }

	public List<Reservation> findAll(){

		Sort sort = Sort.by("bookingDate").ascending();

		return reservationRepository.findAll();
	}
	
	public DiscountCoupon createCoupon(CouponRequest couponRequest){

		DiscountCoupon coupon = new DiscountCoupon(
				couponRequest.couponName(),
				couponRequest.active(),
				couponRequest.percentage()
		);

		discountCouponRepository.save(coupon);
		return  coupon;
	}

	public List<Reservation> shoppingList(String email){
		return findAll().stream().filter(r -> r.getEmail().equals(email))
				.collect(Collectors.toList());
	}

	public void sendingEmail(DadosEmail dados){
		try {
			reservationPublisher.sendingEmail(dados);
		}catch (Exception e){
			throw new SendingEmailException(e.getMessage());
		}
	}
}

