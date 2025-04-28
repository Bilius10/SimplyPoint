package com.Symple.Point.SERVICE;

import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            return usuarioRepositoy.findUserDetailsByCpf(username);
        } catch (UsernameNotFoundException u) {
            throw new RuntimeException(u);
        }


    }
}
