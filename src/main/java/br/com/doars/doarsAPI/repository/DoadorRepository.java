package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.Doador;
import br.com.doars.doarsAPI.domain.SolicitacaoDoacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoadorRepository extends JpaRepository<Doador, Long> {

    @Query(value = "select d.* from doador d where d.ativo = true", nativeQuery = true)
    Page<Doador> findAll(Pageable pageable);

    @Query(value = "select count(*) from doador", nativeQuery = true)
    Long countDoadoresAtivos ();

    List<Doador> findByTipoSanguineoId(Long idTipoSanguineo);

    Boolean existsByContatoEmail(String email);

    @Query(value = "select d.* from doador d, solicitacao_doacao_doadores sd where d.id = sd.doadores_id and sd.solicitacao_doacao_id = ?1 and d.ativo = true", nativeQuery = true)
    List<Doador> listAllDoadoresBySolicitacaoId(Long id);

    @Query(value = "select * from Doador where id_tipo_sanguineo = ?1 and municipios_id in ?2", nativeQuery = true)
    List<Doador> findByTipoSanguineoIdNearByEntidade(Long idTipoSanguineo, List<Long> municipios);

    @Query(value = "select * from Doador where id_tipo_sanguineo in ?1 and municipios_id in ?2", nativeQuery = true)
    List<Doador> findByTipoSanguineoIdListNearByEntidade(List<Long> idTipoSanguineo, List<Long> municipios);

}
