package com.Symple.Point.REPOSITORY;

import com.Symple.Point.ENTITY.HoraPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.time.Month;
import java.util.List;

@Repository
public interface HoraPontoRepository extends JpaRepository<HoraPonto, Long> {

    @Query("select h from HoraPonto h where h.usuario.idUsuario = :idUsuario and h.data = :data")
    List<HoraPonto> findByUsuarioAndData(Long idUsuario, Date data);

    @Query("select h from HoraPonto h JOIN h.usuario u where h.data= :data " +
            "and h.usuario.idUsuario = :idUsuario")
    List<HoraPonto> findHoraPontoByUsuarioAndData(Long idUsuario, Date data);


    @Query("SELECT h FROM HoraPonto h JOIN h.usuario u WHERE FUNCTION('MONTH', h.data) = :mes AND FUNCTION('YEAR', h.data) = :ano " +
            "AND u.cpf = :cpf")
    List<HoraPonto> buscarPorMesEAno(int mes, int ano, String cpf);

    @Query("SELECT count(h) FROM HoraPonto h WHERE h.usuario.idUsuario = :idUsuario and h.data = :data")
    Long countAllByUsuario(Long idUsuario, Date data);
}
