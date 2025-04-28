package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;

import java.sql.Time;
import java.util.List;

public record PontosDoDia(@NotBlank String nomeFuncionario,
                          @NotBlank List<Time> pontosBatidos) {
}
