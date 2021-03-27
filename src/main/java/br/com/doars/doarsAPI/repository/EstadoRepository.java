package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Estados;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estados, Long> {

    @Query(value = "SELECT p FROM Estados p")
    List<Estados> findAllOrderByNome(Sort sort);

    Optional<Estados> findBySigla(String sigla);

}
