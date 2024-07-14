package com.bandeira.reservation.dtos;

public record CouponRequest(

        String couponName,

        Boolean active,

        Double percentage
) {
}
