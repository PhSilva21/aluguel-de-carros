package com.bandeira.reservation.exceptions;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(){
        super("Invalid coupon");
    }

    public CouponNotFoundException(String message){
        super(message);
    }
}
