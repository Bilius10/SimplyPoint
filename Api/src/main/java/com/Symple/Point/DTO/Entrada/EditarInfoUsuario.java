package com.Symple.Point.DTO.Entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record EditarInfoUsuario(@NotBlank String cpf,
                                @NotNull Date horaEntrada,
                                @NotNull Date horaEntradaAlmoco,
                                @NotNull Date horaSaidaAlmoco,
                                @NotNull Date horaSaida,
                                @NotNull Double salario,
                                @NotBlank String cargo) {
}
