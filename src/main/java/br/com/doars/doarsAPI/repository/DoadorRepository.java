package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoadorRepository extends JpaRepository<Doador, Long> {

    List<Doador> findByTipoSanguineoId(Long idTipoSanguineo);

    Boolean existsByContatoEmail(String email);

    @Query(value = "select * from Doador where id_tipo_sanguineo = ?1 and municipios_id in ?2", nativeQuery = true)
    List<Doador> findByTipoSanguineoIdNearByEntidade(Long idTipoSanguineo, List<Long> municipios);

    @Query(value = "select * from Doador where id_tipo_sanguineo in ?1 and municipios_id in ?2", nativeQuery = true)
    List<Doador> findByTipoSanguineoIdListNearByEntidade(List<Long> idTipoSanguineo, List<Long> municipios);

}
