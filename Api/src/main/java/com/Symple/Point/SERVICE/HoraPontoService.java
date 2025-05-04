package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.BaterPonto;
import com.Symple.Point.DTO.Saida.DadosMensaisUsuario;
import com.Symple.Point.DTO.Saida.EnviarEmailDTO;
import com.Symple.Point.DTO.Saida.PontosDoDia;
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

    public HoraPonto baterPonto(BaterPonto baterPonto) throws RegraNegocioException {

        Optional<Usuario> encontrarUsuario = usuarioRepositoy.findById(baterPonto.idUsuario());

        if(encontrarUsuario.isEmpty()){
            throw new RegraNegocioException("Usuario não encontrado");
        }

        Long quantidadeDePontosBatidos = horaPontoRepository.countAllByIdUsuario(baterPonto.idUsuario(),
                Date.valueOf(LocalDate.now()));

        if(quantidadeDePontosBatidos > 4){
            throw new RegraNegocioException("Usuario já bateu os 4 pontos diarios");
        }

        HoraPonto horaPonto = new HoraPonto();

        horaPonto.setUsuario(encontrarUsuario.get());
        horaPonto.setHoraDoPonto(Time.valueOf(LocalTime.now()));
        horaPonto.setData(Date.valueOf(LocalDate.now()));
        horaPonto.setLatitude(baterPonto.latitude());
        horaPonto.setLongitude(baterPonto.longitude());
        emailService.sendEmail(new EnviarEmailDTO(baterPonto.email(),
                "Confirmação do Ponto",
                "Usuario: "+encontrarUsuario.get().getNome()+
                        " \n Horario: "+horaPonto.getHoraDoPonto()+
                        " \n Data: "+horaPonto.getData()+
                        " \n Localização: "+baterPonto.latitude()+" "+baterPonto.longitude()));

        return horaPontoRepository.save(horaPonto);
    }

    public List<HoraPonto> pontosDoDiaPorId(Long idUsuario){

        return horaPontoRepository.findByUsuarioAndData(idUsuario, Date.valueOf(LocalDate.now()));
    }

    public List<PontosDoDia> pontosDoDia(){

        List<PontosDoDia> pontosDoDias = new ArrayList<>();

        List<HoraPonto> listaPontosDoDia = horaPontoRepository.findHoraPontoByData(Date.valueOf(LocalDate.now()));

        Map<Long, List<HoraPonto>> agrupadoPorUsuario = listaPontosDoDia.stream()
                .collect(Collectors.groupingBy(hp -> hp.getUsuario().getIdUsuario()));

        for (Map.Entry<Long, List<HoraPonto>> entry : agrupadoPorUsuario.entrySet()) {
            List<HoraPonto> pontos = entry.getValue();

            String nome = pontos.get(0).getUsuario().getNome();

            List<Time> horas = pontos.stream().map(HoraPonto::getHoraDoPonto).collect(Collectors.toList());

            List<Float> latitude = pontos.stream().map(HoraPonto::getLatitude).collect(Collectors.toList());

            List<Float> longitude = pontos.stream().map(HoraPonto::getLongitude).collect(Collectors.toList());

            pontosDoDias.add(new PontosDoDia(nome, horas, latitude, longitude));
        }
        return pontosDoDias;
    }

    public DadosMensaisUsuario dadosMensaisPorCpf(String cpf) throws RegraNegocioException {

        List<HoraPonto> horas_e_datas_trabalhadas = horaPontoRepository.buscarPorMesEAno(LocalDate.now().getMonth(), LocalDate.now().getYear(),
                cpf.replace(".", "").replace("-", ""));

        if(horas_e_datas_trabalhadas.isEmpty()){
            throw new RegraNegocioException("Esse cpf não esta vinculado a nenhum cpf");
        }


        int diasTrabalhados = (int) horas_e_datas_trabalhadas.stream().map(HoraPonto::getData).distinct().count();
        int diasFaltas = 20 - diasTrabalhados;

        Optional<InfoUsuario> salario_e_cargo = infoUsuarioRepository.findByUsuario(horas_e_datas_trabalhadas.get(0).getUsuario().getIdUsuario());

        if(salario_e_cargo.isEmpty()){
            throw new RegraNegocioException("Não foi definado o salario e cargo desse funcionario");
        }

        Double desconto = (diasFaltas*(salario_e_cargo.get().getSalario()/20));
        double salarioComDesconto = salario_e_cargo.get().getSalario()-desconto;

        Duration horasTrabalhadasNoMes = horas_e_datas_trabalhadas.stream()
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

        return new DadosMensaisUsuario(horas_e_datas_trabalhadas.get(0).getUsuario().getNome(), diasTrabalhados, diasFaltas,
                desconto, salarioComDesconto, salario_e_cargo.get().getCargo(), horasTrabalhadasNoMes);
    }

}
