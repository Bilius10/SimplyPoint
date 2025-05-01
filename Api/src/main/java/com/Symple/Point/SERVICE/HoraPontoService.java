package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.BaterPonto;
import com.Symple.Point.DTO.Saida.DadosMensaisUsuario;
import com.Symple.Point.DTO.Saida.EnviarEmailDTO;
import com.Symple.Point.DTO.Saida.PontosDoDia;
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
import java.util.*;
import java.util.stream.Collectors;

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

    public List<HoraPonto> pontosDoDiaPorId(Long idUsuario){

        return horaPontoRepository.findByUsuarioAndData(idUsuario, Date.valueOf(LocalDate.now()));
    }

    public List<PontosDoDia> pontosDoDia(){

        List<PontosDoDia> pontosDoDias = new ArrayList<>();

        List<HoraPonto> listaPontosDoDia = horaPontoRepository.findHoraPontoByIdHoraPonto(Date.valueOf(LocalDate.now()));

        Map<Long, List<HoraPonto>> agrupadoPorUsuario = listaPontosDoDia.stream()
                .collect(Collectors.groupingBy(hp -> hp.getUsuario().getIdUsuario()));

        for (Map.Entry<Long, List<HoraPonto>> entry : agrupadoPorUsuario.entrySet()) {
            List<HoraPonto> pontos = entry.getValue();

            String nome = pontos.get(0).getUsuario().getNome();

            List<Time> horas = pontos.stream().map(HoraPonto::getHoraDoPonto).collect(Collectors.toList());

            pontosDoDias.add(new PontosDoDia(nome, horas));
        }
        return pontosDoDias;
    }

    public DadosMensaisUsuario dadosMensais(String cpf) throws RegraNegocioException {

        Optional<String> nomeUsuario = usuarioRepositoy.findUsuarioNomeByCpf(
                cpf.replace(".", "").replace("-", ""));

        if(nomeUsuario.isEmpty()){
            throw new RegraNegocioException("Esse cpf não esta vinculado a nenhum cpf");
        }

        return null;
    }

}
