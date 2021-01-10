package br.com.doars.doarsAPI.configuration.security;

import br.com.doars.doarsAPI.service.TokenJJWTService;
import br.com.doars.doarsAPI.domain.Usuario;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import br.com.doars.doarsAPI.util.Validation;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private TokenJJWTService tokenService;
    private UsuarioRepository usuarioRepository;
    private Validation validation;

    public AuthenticationByTokenFilter(TokenJJWTService tokenService, UsuarioRepository usuarioRepository, Validation validation) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.validation = validation;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = recoverHeaderToken(httpServletRequest);

            if(tokenService.isTokenValid(token)){
                authClient(token);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        }catch (SignatureException e ){

            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpServletResponse.getWriter().write(validation.tokenSignatureException(e));

        }catch (ExpiredJwtException e){

            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpServletResponse.getWriter().write(validation.tokenExpiredException(e));

        }catch (Exception e){

            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpServletResponse.getWriter().write(validation.tokenSignatureException(e));

        }

    }

    private void authClient(String token) {

        String userEmail = tokenService.getUserId(token);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(userEmail);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                usuario.get(), null, usuario.get().getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

    private String recoverHeaderToken(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7);

    }

}
