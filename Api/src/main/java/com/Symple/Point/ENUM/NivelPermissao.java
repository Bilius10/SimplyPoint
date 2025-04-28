package com.Symple.Point.ENUM;

public enum NivelPermissao {

    RH("rh"),
    FUNCIONARIO("funcionario");


    private String nivel;

    NivelPermissao(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }
}
