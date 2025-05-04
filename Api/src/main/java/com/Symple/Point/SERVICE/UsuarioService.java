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
        usuario.formatarCpf();

        Optional<Usuario> encontreUsuario = usuarioRepositoy.findUsuarioByCpf(usuario.getUsername());

        if(encontreUsuario.isEmpty()){
            throw new RegraNegocioException("Usuario não existe");
        }

        if (!passwordEncoder.matches(usuario.getPassword(), encontreUsuario.get().getPassword())) {
            throw new RegraNegocioException("Senha incorreta.");
        }

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(encontreUsuario.get().getUsername(),
                usuario.getSenha());

        var auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((com.Symple.Point.ENTITY.Usuario) auth.getPrincipal());

        return new LoginDTO_Resposta(encontreUsuario.get().getIdUsuario(), token,
                encontreUsuario.get().getNome(), encontreUsuario.get().getEmail());
    }

    public RegisterDTO register(Usuario usuario) throws RegraNegocioException{

        boolean validacaoCpf = usuario.validarCpf();

        if(!validacaoCpf){
            throw new RegraNegocioException("Cpf invalido");
        }

        Optional<Usuario> jaExiste = usuarioRepositoy.findUsuarioByCpf(usuario.getCpf());

        if (jaExiste.isPresent()) {
            throw new RegraNegocioException("Cpf já utilizado por outro usuario");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getPassword());
        usuario.setSenha(encryptedPassword);

        usuarioRepositoy.save(usuario);

        return new RegisterDTO(usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getSenha());
    }

}
