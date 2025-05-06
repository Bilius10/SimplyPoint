package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Entrada.RegisterDTO;
import com.Symple.Point.DTO.Saida.LoginDTO_Resposta;
import com.Symple.Point.ENTITY.Usuario;
import com.Symple.Point.EXCEPTIONS.RegraNegocioException;
import com.Symple.Point.INFRASECURITY.TokenService;
import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public LoginDTO_Resposta login(Usuario usuario) throws RegraNegocioException {

        Usuario usuarioBanco = buscarUsuarioPorCpf(usuario.getCpf());

        validarSenha(usuario.getPassword(), usuarioBanco.getPassword());

        var auth = autenticarUsuario(usuarioBanco.getUsername(), usuario.getSenha());

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return new LoginDTO_Resposta(usuarioBanco.getIdUsuario(), token,
                usuarioBanco.getNome(), usuarioBanco.getEmail());
    }

    public Usuario buscarUsuarioPorCpf(String cpf) throws RegraNegocioException {
        Usuario usuario = new Usuario();
        usuario.formatarCpf(cpf);

        return usuarioRepositoy.findUsuarioByCpf(usuario.getCpf())
                .orElseThrow(() -> new RegraNegocioException("Usuario não existe"));
    }

    public void validarSenha(String senhaDigitada, String senhaCriptografada) throws RegraNegocioException {
        if (!passwordEncoder.matches(senhaDigitada, senhaCriptografada)) {
            throw new RegraNegocioException("Senha incorreta");
        }
    }

    public Authentication autenticarUsuario(String username, String senha) {
        var authToken = new UsernamePasswordAuthenticationToken(username, senha);
        return authenticationManager.authenticate(authToken);
    }

    public RegisterDTO register(Usuario usuario) throws RegraNegocioException {
        validarCpf(usuario);
        verificarCpfDuplicado(usuario.getCpf());

        usuario.setSenha(criptografarSenha(usuario.getPassword()));
        usuarioRepositoy.save(usuario);

        return new RegisterDTO(usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getSenha());
    }

    public void validarCpf(Usuario usuario) throws RegraNegocioException {
        if (!usuario.validarCpf()) {
            throw new RegraNegocioException("Cpf invalido");
        }
    }

    public void verificarCpfDuplicado(String cpf) throws RegraNegocioException {
        if (usuarioRepositoy.findUsuarioByCpf(cpf).isPresent()) {
            throw new RegraNegocioException("Cpf já utilizado por outro usuario");
        }
    }

    public String criptografarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

}
