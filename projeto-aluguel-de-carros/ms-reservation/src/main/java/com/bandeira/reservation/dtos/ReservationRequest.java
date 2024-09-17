package com.bandeira.reservation.dtos;

import java.time.LocalDateTime;

public record ReservationRequest(

     String email,

     String car,

     LocalDateTime withdrawal,
    
     LocalDateTime devolution,

     String couponName

     ) {


}
