package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.TokenDTO;
import br.com.doars.doarsAPI.controller.form.LoginForm;
import br.com.doars.doarsAPI.exception.AuthException;
import br.com.doars.doarsAPI.exception.UserEnabledException;
import br.com.doars.doarsAPI.domain.Usuario;
import br.com.doars.doarsAPI.controller.form.AutenticacaoUsuarioForm;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import br.com.doars.doarsAPI.util.Validation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutenticacaoService {

    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;

    private Validation validation;

    public TokenDTO authenticateUsuario(LoginForm loginForm, TokenJJWTService tokenService) {

        try {

            UsernamePasswordAuthenticationToken dataAuth = new UsernamePasswordAuthenticationToken(
                    loginForm.getEmail(), loginForm.getSenha());

            Authentication authentication = authenticationManager.authenticate(dataAuth);

            return tokenService.generateToken(authentication);

        }catch (DisabledException e){

            throw new UserEnabledException();

        }catch (AuthenticationException authenticationException){

            throw new AuthException();

        }

    }

    public void activateUsuario(AutenticacaoUsuarioForm autenticacaoUsuarioForm){

        validation.usuarioOrResourceNotFoundException(usuarioRepository, autenticacaoUsuarioForm.getCodigo());
        Usuario usuario = validation.usuarioByEmailOrResourceNotFoundException(usuarioRepository, autenticacaoUsuarioForm.getEmail());
        usuario.setIsEnabled(true);
        usuarioRepository.save(usuario);

    }

}
