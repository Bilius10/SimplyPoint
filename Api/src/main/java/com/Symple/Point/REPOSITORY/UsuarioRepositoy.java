package com.Symple.Point.REPOSITORY;

import com.Symple.Point.ENTITY.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositoy extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf")
    UserDetails findUserDetailsByCpf(String cpf);

    @Query("SELECT u from Usuario u WHERE u.cpf = :cpf")
    Optional<Usuario> findUsuarioByCpf(String cpf);
}
