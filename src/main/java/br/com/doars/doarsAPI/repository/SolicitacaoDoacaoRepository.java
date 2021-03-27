package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.SolicitacaoDoacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolicitacaoDoacaoRepository extends JpaRepository<SolicitacaoDoacao, Long> {

    @Query(value = "select s.* from solicitacao_doacao s where s.ativo = true", nativeQuery = true)
    Page<SolicitacaoDoacao> findAll(Pageable pageable);

    Page<SolicitacaoDoacao> findAllByEntidadeId(Pageable pageable, Long id);

    @Query(value = "select distinct s.* from solicitacao_doacao s, entidade e, solicitacao_doacao_tipo_sanguineos_list sdt, tipo_sanguineo tp \n" +
            "where s.entidade_id = e.id and s.id = sdt.solicitacao_doacao_id and sdt.tipo_sanguineos_list_id = tp.id and tp.id in ?1", nativeQuery = true)
    Page<SolicitacaoDoacao> findAllByEntidadeIdAndTiposSanguineos(Pageable pageable, List<Long> tiposSanguineosId);

    @Query(value = "select distinct s.* from solicitacao_doacao s, entidade e, solicitacao_doacao_tipo_sanguineos_list sdt, tipo_sanguineo tp \n" +
            "where s.entidade_id = e.id and s.id = sdt.solicitacao_doacao_id and sdt.tipo_sanguineos_list_id = tp.id and s.descricao like %?1%", nativeQuery = true)
    Page<SolicitacaoDoacao> findAllByEntidadeIdAndDescricao(Pageable pageable, String search);

    @Query(value = "select distinct s.* from solicitacao_doacao s, entidade e, solicitacao_doacao_tipo_sanguineos_list sdt, tipo_sanguineo tp \n" +
            "where s.entidade_id = e.id and s.id = sdt.solicitacao_doacao_id and sdt.tipo_sanguineos_list_id = tp.id and tp.id in ?1 and s.descricao like %?2%", nativeQuery = true)
    Page<SolicitacaoDoacao> findAllByEntidadeIdAndTiposSanguineosAndDescricao(Pageable pageable, List<Long> tiposSanguineosId, String search);

}
