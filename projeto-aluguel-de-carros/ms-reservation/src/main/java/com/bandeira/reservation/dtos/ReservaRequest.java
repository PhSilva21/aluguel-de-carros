package com.bandeira.reservation.dtos;

import com.bandeira.reservation.model.DiscountCoupon;

import java.time.LocalDateTime;

public record ReservaRequest(

     String email,

     String carro,

     LocalDateTime retirada,
    
     LocalDateTime devolucao,

     String couponName

     ) {


}
