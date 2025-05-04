package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

public record EditarInfoUsuario(@NotBlank String cpf,
                                Time horaEntrada,
                                Time horaEntradaAlmoco,
                                Time horaSaidaAlmoco,
                                Time horaSaida,
                                Double salario,
                                String cargo) {
}
