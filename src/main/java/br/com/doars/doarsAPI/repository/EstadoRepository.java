package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Estados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estados, Long> {

    Optional<Estados> findBySigla(String sigla);

}
