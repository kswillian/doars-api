package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {
}
