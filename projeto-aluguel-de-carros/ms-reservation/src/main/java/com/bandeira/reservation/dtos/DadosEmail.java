package com.bandeira.reservation.dtos;

public record DadosEmail(

        String ownerRef,

        String emailFrom,


        String emailTo,


        String subject,

        String text

) {
}
