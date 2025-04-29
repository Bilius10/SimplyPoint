package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.EditarInfoUsuario;
import com.Symple.Point.ENTITY.InfoUsuario;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.REPOSITORY.InfoUsuarioRepository;
import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfoUsuarioService {

    @Autowired
    private InfoUsuarioRepository infoUsuarioRepository;
    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    public InfoUsuario editarInfoUsuario(InfoUsuario infoUsuario, String cpf) throws RegraNegocioException {

        Optional<Usuario> usuarioExiste = usuarioRepositoy.findUsuarioByCpf(cpf);

        if(usuarioExiste.isEmpty()){

            throw new RegraNegocioException("Cpf não ligado a nenhum funcionario");
        }

        infoUsuario.setUsuario(usuarioExiste.get());

        return infoUsuarioRepository.save(infoUsuario);
    }
}
