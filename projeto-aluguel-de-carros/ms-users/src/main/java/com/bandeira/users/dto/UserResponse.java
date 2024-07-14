package com.bandeira.users.dto;

public record UserResponse(


         String name,

         String email,

         String password,

         Long cel,

         String cpf
) {
}
