package com.bandeira.ms_email.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(

        @NotBlank
        String ownerRef,

        @NotBlank
        @Email
        String emailFrom,

        @NotBlank
        @Email
        String emailTo,

        @NotBlank
        String subject,

        @NotBlank
        String text
) {
}
