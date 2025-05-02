package com.Symple.Point.REPOSITORY;

import com.Symple.Point.ENTITY.InfoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoUsuarioRepository extends JpaRepository<InfoUsuario, Long> {

    @Query("SELECT i from InfoUsuario i where i.usuario.idUsuario = :idUsuario")
    Optional<InfoUsuario> findByUsuario(Long idUsuario);
}
