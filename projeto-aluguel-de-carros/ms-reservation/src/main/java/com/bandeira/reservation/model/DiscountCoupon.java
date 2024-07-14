package com.bandeira.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "coupons")
@Entity(name = "coupons")
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String couponName;

    private Boolean active;

    private Double discountPercentage;

    public DiscountCoupon (String couponName, Boolean active, Double discountPercentage){
        this.couponName = couponName;
        this.active = active;
        this.discountPercentage = discountPercentage;
    }

    @OneToMany(mappedBy = "coupon")
    List<Reservation> queries = new ArrayList<>();
}
