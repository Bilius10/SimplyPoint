package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.BaterPonto;
import com.Symple.Point.DTO.Saida.EnviarEmailDTO;
import com.Symple.Point.ENTITY.HoraPonto;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.REPOSITORY.HoraPontoRepository;
import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class HoraPontoService {

    @Autowired
    private HoraPontoRepository horaPontoRepository;
    @Autowired
    private UsuarioRepositoy usuarioRepositoy;
    @Autowired
    private EmailService emailService;

    public HoraPonto baterPonto(BaterPonto baterPonto) throws RegraNegocioException {

        Optional<Usuario> encontrarUsuario = usuarioRepositoy.findById(baterPonto.idUsuario());

        if(encontrarUsuario.isEmpty()){
            throw new RegraNegocioException("Usuario não encontrado");
        }

        HoraPonto horaPonto = new HoraPonto();

        horaPonto.setUsuario(encontrarUsuario.get());
        horaPonto.setHoraDoPonto(Time.valueOf(LocalTime.now()));
        horaPonto.setData(Date.valueOf(LocalDate.now()));

        emailService.sendEmail(new EnviarEmailDTO(baterPonto.email(),
                "Confirmação do Ponto",
                encontrarUsuario.get().getNome()+" seu ponto foi confirmado as"+
                        horaPonto.getHoraDoPonto()+" do dia "+horaPonto.getData()));

        return horaPontoRepository.save(horaPonto);
    }

    public List<HoraPonto> pontosDoDia(Long idUsuario){

        return horaPontoRepository.findByUsuarioAndData(idUsuario, Date.valueOf(LocalDate.now()));
    }
}
