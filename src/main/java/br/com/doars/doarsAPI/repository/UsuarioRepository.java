package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCodigoValidacao(String codigoValidacao);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEntidadeId(Long id);

}
