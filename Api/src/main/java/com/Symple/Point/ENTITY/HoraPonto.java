package com.Symple.Point.ENTITY;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HoraPonto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHoraPonto;
    private Date data;
    private Time horaDoPonto;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario") // Chave estrangeira com a tabela 'usuario'
    private Usuario usuario;

    public Long getIdHoraPonto() {
        return idHoraPonto;
    }

    public void setIdHoraPonto(Long idHoraPonto) {
        this.idHoraPonto = idHoraPonto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHoraDoPonto() {
        return horaDoPonto;
    }

    public void setHoraDoPonto(Time horaDoPonto) {
        this.horaDoPonto = horaDoPonto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
