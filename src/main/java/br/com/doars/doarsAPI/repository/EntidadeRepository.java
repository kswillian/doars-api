package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

    Boolean existsByCnpj(String cnpj);

    Boolean existsByContatoEmail(String email);

    Optional<Entidade> findByContatoEmail(String email);

}
