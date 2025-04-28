package com.Symple.Point.CONTROLLER;

import com.Symple.Point.DTO.Entrada.LoginDTO_Recebe;
import com.Symple.Point.DTO.Entrada.RegisterDTO;
import com.Symple.Point.DTO.Saida.ErroDTO;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.SERVICE.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO_Recebe loginDTORecebe) throws RegraNegocioException {

        try {

            Usuario usuario = new Usuario();
            BeanUtils.copyProperties(loginDTORecebe, usuario);

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.login(usuario));
        }catch (RegraNegocioException m){

            ErroDTO erroDTO = new ErroDTO(m.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) throws RegraNegocioException{

        try {
            Usuario usuario = new Usuario();
            BeanUtils.copyProperties(registerDTO, usuario);

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.register(usuario));
        }catch (RegraNegocioException m){
            ErroDTO erroDTO = new ErroDTO(m.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
        }
    }

}
