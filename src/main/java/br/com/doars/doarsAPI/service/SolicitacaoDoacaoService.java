package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.form.SolicitacaoDoacaoForm;
import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import br.com.doars.doarsAPI.domain.Entidade;
import br.com.doars.doarsAPI.domain.SolicitacaoDoacao;
import br.com.doars.doarsAPI.domain.TipoSanguineo;
import br.com.doars.doarsAPI.controller.dto.SolicitacaoDoacaoDTO;
import br.com.doars.doarsAPI.controller.form.SolicitacaoDoacaoFormUpdate;
import br.com.doars.doarsAPI.repository.EntidadeRepository;
import br.com.doars.doarsAPI.repository.SolicitacaoDoacaoRepository;
import br.com.doars.doarsAPI.repository.TipoSanguineoRepository;
import br.com.doars.doarsAPI.util.Utilidades;
import br.com.doars.doarsAPI.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class SolicitacaoDoacaoService {

    private SolicitacaoDoacaoRepository solicitacaoDoacaoRepository;
    private TipoSanguineoRepository tipoSanguineoRepository;
    private EntidadeRepository entidadeRepository;

    private DoadorService doadorService;
    private EmailService emailService;
    private Utilidades utilidades;
    private Validation validation;

    public SolicitacaoDoacaoDTO register(SolicitacaoDoacaoForm solicitacaoDoacaoForm){

        Entidade entidade = validation.entidadeOrResourceNotFoundException(entidadeRepository, solicitacaoDoacaoForm.getIdEntidade());

        Set<TipoSanguineo> tipoSanguineoSet = new HashSet<>();
        List<TipoSanguineo> tipoSanguineos = new ArrayList<>();
        List<Long> tipoSanguineoList = new ArrayList<>();

        for(Long tipoSanguineoId: solicitacaoDoacaoForm.getTipoSanguineosList()){
            tipoSanguineoSet.add(validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineos.add(validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineoList.add(tipoSanguineoId);
        }

        List<DoadorDTO> doadorDTOS = doadorService.listAllByTipoSanguineoListAndMunicipio(tipoSanguineoList, entidade.getEndereco().getMunicipios().getId(), solicitacaoDoacaoForm.getDistancia());

        SolicitacaoDoacao solicitacaoDoacao = new SolicitacaoDoacao();
        solicitacaoDoacao.setEntidade(entidade);
        solicitacaoDoacao.setTipoSanguineosList(tipoSanguineos);
        solicitacaoDoacao.setDistancia(solicitacaoDoacaoForm.getDistancia());
        solicitacaoDoacao.setDescricao(solicitacaoDoacaoForm.getDescricao());
        solicitacaoDoacao.setDoadoresNotificados(doadorDTOS.size());

        SolicitacaoDoacao solicitacaoDoacaoRegistered = solicitacaoDoacaoRepository.save(solicitacaoDoacao);

        emailService.sendEmailSolicitacao(utilidades.findAllEmailFromDoador(doadorDTOS), entidade,
                utilidades.convertSetToList(tipoSanguineoSet));

        return new SolicitacaoDoacaoDTO(solicitacaoDoacaoRegistered);

    }

    public Page<SolicitacaoDoacaoDTO> listAll(Pageable pageable){
        Page<SolicitacaoDoacaoDTO> solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(solicitacaoDoacaoRepository.findAll(pageable));
        return solicitacoesDoacao;
    }

    public SolicitacaoDoacaoDTO listById(Long id){
        SolicitacaoDoacao solicitacaoDoacao = validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository, id);
        return new SolicitacaoDoacaoDTO(solicitacaoDoacao);
    }

    @Transactional
    public Page<SolicitacaoDoacaoDTO> listAllByEntidadeId(Pageable pageable, Long id){
        validation.entidadeOrResourceNotFoundException(entidadeRepository, id);
        Page<SolicitacaoDoacaoDTO> solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(solicitacaoDoacaoRepository.findAllByEntidadeId(pageable, id));
        return solicitacoesDoacao;
    }

    public SolicitacaoDoacaoDTO update(SolicitacaoDoacaoFormUpdate solicitacaoDoacaoFormUpdate){

        Entidade entidade = entidadeRepository.findById(solicitacaoDoacaoFormUpdate.getIdEntidade()).get();

        Set<TipoSanguineo> tipoSanguineoSet = new HashSet<>();
        List<TipoSanguineo> tipoSanguineos = new ArrayList<>();
        List<Long> tipoSanguineoList = new ArrayList<>();

        for(Long tipoSanguineoId: solicitacaoDoacaoFormUpdate.getTipoSanguineosList()){
            tipoSanguineoSet.add(validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineos.add(validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineoList.add(tipoSanguineoId);
        }

        SolicitacaoDoacao solicitacaoDoacao = validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository,
                solicitacaoDoacaoFormUpdate.getId()
        );

        solicitacaoDoacao.setEntidade(entidade);
        solicitacaoDoacao.setTipoSanguineosList(tipoSanguineos);
        solicitacaoDoacao.setDescricao(solicitacaoDoacaoFormUpdate.getDescricao());

        SolicitacaoDoacao solicitacaoDoacaoRegistered = solicitacaoDoacaoRepository.save(solicitacaoDoacao);

        return new SolicitacaoDoacaoDTO(solicitacaoDoacaoRegistered);

    }

    public void deleteById(Long id){
        validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository, id);
        solicitacaoDoacaoRepository.deleteById(id);
    }

}
