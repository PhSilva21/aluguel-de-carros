package com.bandeira.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "reservations")
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String car;

    private LocalDateTime withdrawal;

    private LocalDateTime devolution;

    private StatusReservation statusReservation;

    private Double value;

    private LocalDateTime bookingDate;

    public Reservation(String email, String car, LocalDateTime withdrawal, LocalDateTime devolution,
                       StatusReservation statusReservation, Double value, LocalDateTime bookingDate) {
        this.email = email;
        this.car = car;
        this.withdrawal = withdrawal;
        this.devolution = devolution;
        this.statusReservation = statusReservation;
        this.value = value;
        this.bookingDate = bookingDate;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private DiscountCoupon discountCoupon;
}
