package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record BaterPonto(@NotNull Long idUsuario,
                         @NotBlank String email) {
}
