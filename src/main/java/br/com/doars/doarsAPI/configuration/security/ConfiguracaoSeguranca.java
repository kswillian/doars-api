package br.com.doars.doarsAPI.configuration.security;

import br.com.doars.doarsAPI.service.TokenJJWTService;
import br.com.doars.doarsAPI.service.UsuarioService;
import br.com.doars.doarsAPI.util.Validation;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    private TokenJJWTService tokenService;
    private Validation validation;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers(HttpMethod.GET, "/*").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/*").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/health/*").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/info").permitAll()

                .antMatchers(HttpMethod.POST, "/api/v1/entidades").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/doadores").permitAll()

                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/autenticar").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/ativar").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/localidades/paises").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/paises/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/estados").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/estados/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/municipios").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/municipios/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/estados/sigla/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/municipios/*/proximidades").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/localidades/municipios/estados/*").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/tipos-sanguineos").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/tipos-sanguineos/*").permitAll()

                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationByTokenFilter(
                        tokenService, usuarioRepository, validation), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }

}
