package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

public record EditarInfoUsuario(@NotBlank String cpf,
                                @NotNull Time horaEntrada,
                                @NotNull Time horaEntradaAlmoco,
                                @NotNull Time horaSaidaAlmoco,
                                @NotNull Time horaSaida,
                                @NotNull Double salario,
                                @NotBlank String cargo) {
}
