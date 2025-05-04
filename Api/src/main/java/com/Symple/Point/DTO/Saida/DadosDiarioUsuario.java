package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public record DadosDiarioUsuario(@NotBlank String nomeUsuario,
                                 @NotNull Date dia,
                                 @NotNull List<Time> horarios,
                                 @NotNull List<Float> latitudes,
                                 @NotNull List<Float> longitudes) {
}
