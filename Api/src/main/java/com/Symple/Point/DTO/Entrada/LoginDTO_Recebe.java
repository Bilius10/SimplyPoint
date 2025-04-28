package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO_Recebe(@NotBlank String cpf,
                              @NotBlank String senha) {
}
