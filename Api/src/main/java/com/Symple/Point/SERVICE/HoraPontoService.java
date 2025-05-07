package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.BaterPonto;
import com.Symple.Point.DTO.Saida.DadosDiarioUsuario;
import com.Symple.Point.DTO.Saida.DadosMensaisUsuario;
import com.Symple.Point.DTO.Saida.EnviarEmailDTO;
import com.Symple.Point.ENTITY.HoraPonto;
import com.Symple.Point.ENTITY.InfoUsuario;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.REPOSITORY.HoraPontoRepository;
import com.Symple.Point.REPOSITORY.InfoUsuarioRepository;
import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
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
    private InfoUsuarioRepository infoUsuarioRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioService usuarioService;

    public HoraPonto baterPontoeEnviarEmail(BaterPonto baterPonto) throws RegraNegocioException {

        EnviarEmailDTO emailDTO = new EnviarEmailDTO(
                baterPonto.email(),
                "Confirmação do Ponto",
                "Horário: " + Time.valueOf(LocalTime.now()) +
                        "\nData: " + Date.valueOf(LocalDate.now()) +
                        "\nLocalização: " + baterPonto.latitude() + " " + baterPonto.longitude()
        );

        Thread enviarEmail = new Thread(() -> emailService.sendEmail(emailDTO));
        enviarEmail.start();

        HoraPonto horaPonto = baterPonto(baterPonto);

        return horaPonto;
    }

    public HoraPonto baterPonto(BaterPonto baterPonto) throws RegraNegocioException {

        Optional<Usuario> encontrarUsuarioPeloId = encontrarUsuarioPeloId(baterPonto.idUsuario());

        Long quantidadeDePontosBatidos = quantidadeDePontosBatidos(baterPonto.idUsuario());

        HoraPonto horaPonto = new HoraPonto();

        horaPonto.setUsuario(encontrarUsuarioPeloId.get());
        horaPonto.setHoraDoPonto(Time.valueOf(LocalTime.now()));
        horaPonto.setData(Date.valueOf(LocalDate.now()));
        horaPonto.setLatitude(baterPonto.latitude());
        horaPonto.setLongitude(baterPonto.longitude());

        return horaPontoRepository.save(horaPonto);
    }

    public Optional<Usuario> encontrarUsuarioPeloId(Long id) throws RegraNegocioException {
        Optional<Usuario> encontrarUsuarioPeloId = usuarioRepositoy.findById(id);

        if(encontrarUsuarioPeloId.isEmpty()){
            throw new RegraNegocioException("Usuario não encontrado");
        }

        return encontrarUsuarioPeloId;
    }

    public Long quantidadeDePontosBatidos(Long id) throws RegraNegocioException {
        Long quantidadeDePontosBatidos = horaPontoRepository.countAllByUsuario(id,
                Date.valueOf(LocalDate.now()));

        if(quantidadeDePontosBatidos > 4){
            throw new RegraNegocioException("Usuario já bateu os 4 pontos diarios");
        }

        return quantidadeDePontosBatidos;
    }

    public List<HoraPonto> pontosDoDiaPorId(Long idUsuario){

        return horaPontoRepository.findByUsuarioAndData(idUsuario, Date.valueOf(LocalDate.now()));
    }

    public DadosDiarioUsuario pontosDoDiaPorCPf(String cpf) throws RegraNegocioException {

        Optional<Usuario> usuarioByCpf = Optional.ofNullable(usuarioService.buscarUsuarioPorCpf(cpf));

        List<HoraPonto> horaPontoByUsuarioAndData = buscarHoraPontoPorIdUsuarioEData(usuarioByCpf.get().getIdUsuario());

        return criarDadosDiarios(horaPontoByUsuarioAndData);
    }

    public List<HoraPonto> buscarHoraPontoPorIdUsuarioEData(Long id) throws RegraNegocioException {
        List<HoraPonto> horaPontoByUsuarioAndData = horaPontoRepository.findHoraPontoByUsuarioAndData(id,
                Date.valueOf(LocalDate.now()));

        if(horaPontoByUsuarioAndData.isEmpty()){
            throw new RegraNegocioException("Nenhum ponto registrado para esse funcionario");
        }

        return horaPontoByUsuarioAndData;
    }

    public DadosDiarioUsuario criarDadosDiarios(List<HoraPonto> horaPontos){
        String nome = horaPontos.get(0).getUsuario().getNome();
        Date data = new java.sql.Date(horaPontos.get(0).getData().getTime());
        List<Time> horas = horaPontos.stream().map(HoraPonto::getHoraDoPonto).toList();
        List<Float> latitudes = horaPontos.stream().map(HoraPonto::getLatitude).toList();
        List<Float> longitudes = horaPontos.stream().map(HoraPonto::getLongitude).toList();

        return new DadosDiarioUsuario(nome, data, horas, latitudes,longitudes);
    }


    public DadosMensaisUsuario dadosMensais(String cpf, int mes, int ano) throws RegraNegocioException {

        verificarMeseAno(mes, ano);

        List<HoraPonto> horaPontoPorMeseAno = buscarHoraPontoPorMeseAno(cpf, mes, ano);

        Optional<InfoUsuario> usuarioPorId = buscarUsuarioPorId(horaPontoPorMeseAno.get(0).getUsuario().getIdUsuario());

        Duration horasTrabalhadasNoMes = retornarHorasTrabalhadasNoMes(horaPontoPorMeseAno);

        return criarDadosMensais(horaPontoPorMeseAno, usuarioPorId, horasTrabalhadasNoMes);
    }

    public void verificarMeseAno(int mes, int ano) throws RegraNegocioException {
        if(mes <= 0){
            throw new RegraNegocioException("Mês não existe");
        }
        if(ano <= 0){
            throw new RegraNegocioException("Ano não existe");
        }

    }

    public List<HoraPonto> buscarHoraPontoPorMeseAno(String cpf, int mes, int ano) throws RegraNegocioException {
        List<HoraPonto> horaPontosPorMesEAno = horaPontoRepository.buscarPorMesEAno(mes, ano,
                cpf.replace(".", "").replace("-", ""));

        if(horaPontosPorMesEAno.isEmpty()){
            throw new RegraNegocioException("Cpf não vinculado a nenhum funcionario");
        }

        return horaPontosPorMesEAno;
    }

    public Duration retornarHorasTrabalhadasNoMes(List<HoraPonto> horaPontos){
        return horaPontos.stream()
                .collect(Collectors.groupingBy(h -> h.getData().toString()))
                .values().stream()
                .map(lista -> {
                    var horarios = lista.stream()
                            .map(h -> h.getHoraDoPonto().toLocalTime())
                            .sorted()
                            .toList();

                    Duration dia = Duration.ZERO;
                    for (int i = 0; i + 1 < horarios.size(); i += 2)
                        dia = dia.plus(Duration.between(horarios.get(i), horarios.get(i + 1)));
                    return dia;
                })
                .reduce(Duration.ZERO, Duration::plus);
    }

    public Optional<InfoUsuario> buscarUsuarioPorId(Long id) throws RegraNegocioException {
        Optional<InfoUsuario> infoUsuarioPorIdUsuario= infoUsuarioRepository.findByUsuario(id);

        if(infoUsuarioPorIdUsuario.isEmpty()){
            throw new RegraNegocioException("Não foi definado o salario e cargo desse funcionario");
        }

        return infoUsuarioPorIdUsuario;
    }

    public DadosMensaisUsuario criarDadosMensais(List<HoraPonto> horaPontoPorMeseAno, Optional<InfoUsuario> usuarioPorId,
                                                 Duration horasTrabalhadasNoMes){

        int diasTrabalhados = (int) horaPontoPorMeseAno.stream().map(HoraPonto::getData).distinct().count();
        int diasFaltas = 20 - diasTrabalhados;

        Double desconto = (diasFaltas*(usuarioPorId.get().getSalario()/20));
        double salarioComDesconto = usuarioPorId.get().getSalario()-desconto;

        return new DadosMensaisUsuario(horaPontoPorMeseAno.get(0).getUsuario().getNome(), diasTrabalhados, diasFaltas,
                desconto, salarioComDesconto, usuarioPorId.get().getCargo(), horasTrabalhadasNoMes);
    }

}
