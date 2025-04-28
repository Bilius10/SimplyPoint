package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String nome,
                          @NotBlank String cpf,
                          @NotBlank String email,
                          @NotBlank String senha) {
}
