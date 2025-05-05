package com.Symple.Point.DTO.Saida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

public record DadosMensaisUsuario(@NotBlank String nomeUsuario,
                                  @NotNull int diasTrabalhados,
                                  @NotNull int diasFaltados,
                                  @NotNull double descontoPorFalta,
                                  @NotNull double salarioComDesconto,
                                  @NotBlank String cargo,
                                  @NotNull Duration horasTrabalhadasNoMes
                                  ) {
}
