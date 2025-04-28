package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;

public record EnviarEmailDTO(@NotBlank String emailTo,
                             @NotBlank String subject,
                             @NotBlank String text) {
}
