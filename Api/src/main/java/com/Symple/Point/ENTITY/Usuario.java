package com.Symple.Point.ENTITY;

import com.Symple.Point.ENUM.NivelPermissao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
public class Usuario implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private NivelPermissao nivelPermissao;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public Usuario() {
        this.nivelPermissao = NivelPermissao.FUNCIONARIO;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public void formatarCpf(String cpf){

        this.cpf = cpf.replace(".", "").replace("-", "");
    }

    public boolean validarCpf() {
        formatarCpf(cpf);

        if (this.cpf == null || this.cpf.length() != 11) {
            return false;
        }

        if (this.cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        char[] arrayDoCpf = this.cpf.toCharArray();

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(arrayDoCpf[i]) * (10 - i);
        }
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(arrayDoCpf[i]) * (11 - i);
        }
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

        return digito1 == Character.getNumericValue(arrayDoCpf[9]) &&
                digito2 == Character.getNumericValue(arrayDoCpf[10]);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.nivelPermissao == NivelPermissao.RH){
            return List.of(new SimpleGrantedAuthority("ROLE_RH"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public NivelPermissao getNivelPermissao() {
        return nivelPermissao;
    }

    public void setNivelPermissao(NivelPermissao nivelPermissao) {
        this.nivelPermissao = nivelPermissao;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
