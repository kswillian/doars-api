package br.com.doars.doarsAPI.repository;

import br.com.doars.doarsAPI.domain.SolicitacaoDoacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoDoacaoRepository extends JpaRepository<SolicitacaoDoacao, Long> {

    Page<SolicitacaoDoacao> findAllByEntidadeId(Pageable pageable, Long id);

}
