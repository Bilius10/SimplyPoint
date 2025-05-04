package com.Symple.Point.ENTITY;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;
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
    private float latitude;
    private float longitude;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
