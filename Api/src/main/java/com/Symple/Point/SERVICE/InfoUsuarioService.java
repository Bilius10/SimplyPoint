package com.Symple.Point.SERVICE;

import com.Symple.Point.ENTITY.InfoUsuario;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.REPOSITORY.InfoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfoUsuarioService {

    @Autowired
    private InfoUsuarioRepository infoUsuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    public InfoUsuario editarInfoUsuario(InfoUsuario infoUsuario, String cpf) throws RegraNegocioException {

        Optional<Usuario> usuarioByCpf = Optional.ofNullable(usuarioService.buscarUsuarioPorCpf(cpf));

        Optional<InfoUsuario> byUsuario = infoUsuarioRepository.findByUsuario(usuarioByCpf.get().getIdUsuario());

        byUsuario.ifPresent(usuario -> infoUsuario.setIdInfoUsuario(usuario.getIdInfoUsuario()));

        infoUsuario.setUsuario(usuarioByCpf.get());

        return infoUsuarioRepository.save(infoUsuario);
    }
}
