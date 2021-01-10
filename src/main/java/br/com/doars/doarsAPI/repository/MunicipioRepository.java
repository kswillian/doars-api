package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Estados;
import br.com.doars.doarsAPI.domain.Municipios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipios, Long> {

    List<Municipios> findAllByEstados(Estados estados);

    @Query(value = "select * from Municipios where (6371 * acos(cos(radians(?1)) * cos(radians(latitude)) * cos(radians(?2) - radians(longitude)) + sin(radians(?1)) * sin(radians(latitude)))) <= ?3", nativeQuery = true)
    List<Municipios> findAllMunicipiosNearby(BigDecimal latitude, BigDecimal longitude, Long kilometers);

}
