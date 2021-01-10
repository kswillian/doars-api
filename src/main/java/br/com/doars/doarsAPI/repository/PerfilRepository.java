package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil findByDescricao(String descricao);

}
