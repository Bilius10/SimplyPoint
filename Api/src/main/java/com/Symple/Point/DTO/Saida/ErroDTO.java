package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;

public record ErroDTO(@NotBlank String mensagem){
}
