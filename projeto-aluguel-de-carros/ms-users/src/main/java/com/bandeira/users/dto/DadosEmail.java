package com.bandeira.users.dto;

public record DadosEmail(

        String ownerRef,

        String emailFrom,


        String emailTo,


        String subject,

        String text

) {
}
