package com.Symple.Point.CONTROLLER;

import com.Symple.Point.DTO.Entrada.EditarInfoUsuario;
import com.Symple.Point.DTO.Saida.ErroDTO;
import com.Symple.Point.ENTITY.InfoUsuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.SERVICE.InfoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/infoUsuario")
public class InfoUsuarioController {

    @Autowired
    private InfoUsuarioService infoUsuarioService;

    @PutMapping
    public ResponseEntity<Object> editarInfoUsuario(@RequestBody @Valid EditarInfoUsuario editarInfoUsuario) throws RegraNegocioException {

        try {

            InfoUsuario infoUsuario = new InfoUsuario();
            BeanUtils.copyProperties(editarInfoUsuario, infoUsuario,  "cpf");

            return ResponseEntity.status(HttpStatus.OK).body(infoUsuarioService.editarInfoUsuario(infoUsuario,
                    editarInfoUsuario.cpf()));

        }catch (RegraNegocioException m){

            ErroDTO erroDTO = new ErroDTO(m.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO.mensagem());
        }
    }

}
