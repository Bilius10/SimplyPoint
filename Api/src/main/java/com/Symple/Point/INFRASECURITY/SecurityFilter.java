package com.Symple.Point.INFRASECURITY;

import com.Symple.Point.REPOSITORY.UsuarioRepositoy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepositoy usuarioRepositoy;

    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioRepositoy usuarioRepositoy) {
        this.tokenService = tokenService;
        this.usuarioRepositoy = usuarioRepositoy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoreverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails user = usuarioRepositoy.findUserDetailsByCpf(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoreverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("authorization");
        if (authHeader == null) {
            return null;
        } else {
            return authHeader.replace("Bearer", "").trim();
        }
    }
}
