package com.Symple.Point.REPOSITORY;

import com.Symple.Point.ENTITY.HoraPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface HoraPontoRepository extends JpaRepository<HoraPonto, Long> {

    @Query("select h from HoraPonto h where h.usuario.idUsuario = :idUsuario and h.data = :data")
    List<HoraPonto> findByUsuarioAndData(Long idUsuario, Date data);
}
