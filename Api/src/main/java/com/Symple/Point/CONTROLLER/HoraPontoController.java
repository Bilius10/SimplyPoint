package com.Symple.Point.CONTROLLER;

import com.Symple.Point.DTO.Entrada.BaterPonto;
import com.Symple.Point.DTO.Saida.ErroDTO;
import com.Symple.Point.ENTITY.HoraPonto;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.SERVICE.HoraPontoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ponto")
public class HoraPontoController {

    @Autowired
    private HoraPontoService horaPontoService;

    @PostMapping("/baterPonto")
    public ResponseEntity<Object> baterPonto(@RequestBody @Valid BaterPonto baterPonto) throws RegraNegocioException {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(horaPontoService.baterPontoeEnviarEmail(baterPonto));
        }catch (RegraNegocioException m ){
            ErroDTO erroDTO = new ErroDTO(m.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
        }
    }

    @GetMapping("/pontoDoDia/id/{idUsuario}")
    public ResponseEntity<List<HoraPonto>> pontoDoDiaPorId(@PathVariable Long idUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(horaPontoService.pontosDoDiaPorId(idUsuario));
    }

    @GetMapping("/pontoDoDia/cpf/{cpf}")
    public ResponseEntity<Object> pontoDoDiaPorCpf(@PathVariable String cpf) throws RegraNegocioException {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(horaPontoService.pontosDoDiaPorCPf(cpf));
        }catch (RegraNegocioException m ){
            ErroDTO erroDTO = new ErroDTO(m.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
        }
    }

    @GetMapping("/dadosMensais/{cpf}")
    public ResponseEntity<Object> dadosMensais(@PathVariable String cpf) throws RegraNegocioException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(horaPontoService.dadosMensais(cpf));
        } catch (RegraNegocioException m) {

            ErroDTO erroDTO = new ErroDTO(m.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
        }
    }

}
