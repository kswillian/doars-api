package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.TokenDTO;
import br.com.doars.doarsAPI.domain.Usuario;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import br.com.doars.doarsAPI.util.Validation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenJJWTService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Validation validation;

    @Value("${api.jwt.secret}")
    private String secret;

    @Value("${api.jwt.expiration}")
    private String expiration;

    public TokenDTO generateToken(Authentication authentication){

        Usuario usuario = (Usuario) authentication.getPrincipal();

        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime dataExpiracao = hoje.plusMinutes(Long.parseLong(expiration) / 60000);

        String tokenJwts = Jwts.builder()
                .setIssuer("SpringBoot JWT")
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date( new Date().getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        TokenDTO tokenDTO = TokenDTO.builder()
                .tipo("Bearer")
                .token(tokenJwts)
                .dataGeracao(hoje)
                .dataExpiracao(dataExpiracao)
                .build();

        return tokenDTO;

    }

    public boolean isTokenValid(String headerToken) throws Exception{
        if(headerToken != null){
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(headerToken);
            return true;
        }
        return false;
    }

    public String getUserId(String token){
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
