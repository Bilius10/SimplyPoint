package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.List;

public record PontosDoDia(@NotBlank String nomeFuncionario,
                          @NotNull List<Time> pontosBatidos,
                          @NotNull List<Float> latitude,
                          @NotNull List<Float> longitude) {
}
