package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

    @Query(value = "select count(*) from entidade", nativeQuery = true)
    Long countEntidadesAtivas();

    Boolean existsByCnpj(String cnpj);

    Boolean existsByContatoEmail(String email);

    Optional<Entidade> findByContatoEmail(String email);

}
