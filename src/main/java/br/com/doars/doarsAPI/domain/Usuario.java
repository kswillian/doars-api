package br.com.doars.doarsAPI.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfils;

    private String codigoValidacao;

    private Boolean isNonExpired = false;

    private Boolean isNonLocked = false;

    private Boolean isCredentialsNonExpired = false;

    private Boolean isEnabled = false;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    private LocalDateTime dataAlteracaoSenha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doador_id")
    private Doador doador;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidade_id")
    private Entidade entidade;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfils;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
