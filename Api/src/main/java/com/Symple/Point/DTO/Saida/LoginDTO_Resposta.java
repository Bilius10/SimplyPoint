package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO_Resposta(@NotBlank Long idUsuario,
                                @NotBlank String token,
                                @NotBlank String nome,
                                @NotBlank String email) {
}
