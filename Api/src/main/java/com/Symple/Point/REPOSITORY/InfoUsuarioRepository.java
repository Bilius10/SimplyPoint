package com.Symple.Point.REPOSITORY;

import com.Symple.Point.ENTITY.InfoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoUsuarioRepository extends JpaRepository<InfoUsuario, Long> {
}
