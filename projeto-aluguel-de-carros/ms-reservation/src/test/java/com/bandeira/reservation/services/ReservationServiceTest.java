package com.bandeira.reservation.services;

import com.bandeira.reservation.dtos.ReservationRequest;
import com.bandeira.reservation.infra.CarroFeign;
import com.bandeira.reservation.infra.ClientFeign;
import com.bandeira.reservation.model.Car;
import com.bandeira.reservation.model.Client;
import com.bandeira.reservation.model.Reservation;
import com.bandeira.reservation.model.StatusReservation;
import com.bandeira.reservation.repositories.DiscountCouponRepository;
import com.bandeira.reservation.repositories.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private DiscountCouponRepository discountCouponRepository;

    @Captor
    private ArgumentCaptor<Reservation> reservationArgumentCaptor;

    @Nested
    class maReservation {

        Client client = new Client("Juliana Faria", "julia@gmail.com", "r2e25er52ftf"
                , "7317317353151", true);

        ReservationRequest reservationRequest = new ReservationRequest("mateus@gmail.com", "Civic"
                , LocalDateTime.of(2024,6, 12, 8, 35)
                , LocalDateTime.of(2024,6, 12, 8, 35), "FeriasDay");

        Car car = new Car(12L, "Civic", BigDecimal.valueOf(127.0), true, "Sedan");

        Reservation reservation = new Reservation(21L, "exemplo@email.com", "Toyota Corolla"
                , LocalDateTime.of(2024, 9, 10, 14, 30), LocalDateTime.of(2024, 9, 10, 14, 30)
                , StatusReservation.CONFIRMED, BigDecimal.valueOf(87.50), LocalDateTime.now(), null);

        @Test
        void makeRseservation() throws JsonProcessingException {
            doReturn(reservation)
                    .when(reservationRepository).save(reservationArgumentCaptor.capture());

            var days = ChronoUnit.DAYS.between(reservationRequest.withdrawal()
                    , reservationRequest.devolution());

            var response = reservationService.makeReservation(reservationRequest);

            var reservationCaptured = reservationArgumentCaptor.getValue();

            assertEquals(reservationRequest.email(), reservationCaptured.getEmail());
            assertEquals(reservationRequest.car(), reservationCaptured.getCar());
            assertEquals(reservationRequest.withdrawal(), reservationCaptured.getWithdrawal());
            assertEquals(reservationRequest.devolution(), reservationCaptured.getDevolution());
            assertEquals(reservationRequest.couponName(), reservationCaptured.getDiscountCoupon().getCouponName());
            assertEquals(car.getDailyPrice().multiply(BigDecimal.valueOf(days))
                    , reservationCaptured.getValue());

        }
    }

    @Test
    void discountCoupon() {
    }

    @Test
    void findAll() {
    }

    @Test
    void createCoupon() {
    }

    @Test
    void shoppingList() {
    }

    @Test
    void sendingEmail() {
    }
}