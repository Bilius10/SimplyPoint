package com.Symple.Point.ENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InfoUsuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfoUsuario;
    private Time horaEntrada;
    private Time horaEntradaAlmoco;
    private Time horaSaidaAlmoco;
    private Time horaSaida;
    private Double salario;
    private String cargo;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario") // Chave estrangeira com a tabela 'usuario'
    private Usuario usuario;

    public Long getIdInfoUsuario() {
        return idInfoUsuario;
    }

    public void setIdInfoUsuario(Long idInfoUsuario) {
        this.idInfoUsuario = idInfoUsuario;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraEntradaAlmoco() {
        return horaEntradaAlmoco;
    }

    public void setHoraEntradaAlmoco(Time horaEntradaAlmoco) {
        this.horaEntradaAlmoco = horaEntradaAlmoco;
    }

    public Time getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(Time horaSaidaAlmoco) {
        this.horaSaidaAlmoco = horaSaidaAlmoco;
    }

    public Time getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Time horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
