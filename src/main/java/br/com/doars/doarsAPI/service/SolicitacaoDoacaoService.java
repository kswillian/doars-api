package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.form.SolicitacaoDoacaoForm;
import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import br.com.doars.doarsAPI.domain.Doador;
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

        Entidade entidade = validation.entidadeOrResourceNotFoundException(
                entidadeRepository, solicitacaoDoacaoForm.getIdEntidade());

        Set<TipoSanguineo> tipoSanguineoSet = new HashSet<>();
        List<TipoSanguineo> tipoSanguineos = new ArrayList<>();
        List<Long> tipoSanguineoList = new ArrayList<>();

        for(Long tipoSanguineoId: solicitacaoDoacaoForm.getTiposSanguineos()){
            tipoSanguineoSet.add(validation.tipoSanguineoOrResourceNotFoundException(
                    tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineos.add(validation.tipoSanguineoOrResourceNotFoundException(
                    tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineoList.add(tipoSanguineoId);
        }

        List<Doador> doadores = doadorService.listAllDoadorTipoSanguineoListAndMunicipio(tipoSanguineoList,
                entidade.getEndereco().getMunicipios().getId(), solicitacaoDoacaoForm.getDistancia());

        SolicitacaoDoacao solicitacaoDoacao = new SolicitacaoDoacao();
        solicitacaoDoacao.setEntidade(entidade);
        solicitacaoDoacao.setDoadores(doadores);
        solicitacaoDoacao.setTiposSanguineos(tipoSanguineos);
        solicitacaoDoacao.setDistancia(solicitacaoDoacaoForm.getDistancia());
        solicitacaoDoacao.setDescricao(solicitacaoDoacaoForm.getDescricao());
        solicitacaoDoacao.setDoadoresNotificados(doadores.size());

        SolicitacaoDoacao solicitacaoDoacaoRegistered = solicitacaoDoacaoRepository.save(solicitacaoDoacao);

        emailService.sendEmailSolicitacao(utilidades.findAllEmailFromDoador(DoadorDTO.converterMotelToDTO(doadores)), entidade,
                utilidades.convertSetToList(tipoSanguineoSet));

        return new SolicitacaoDoacaoDTO(solicitacaoDoacaoRegistered);

    }

    public Page<SolicitacaoDoacaoDTO> listAll(Pageable pageable){
        Page<SolicitacaoDoacaoDTO> solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                solicitacaoDoacaoRepository.findAll(pageable));
        return solicitacoesDoacao;
    }

    public Long countSolicitacoesDoacaoAtivas(){
        return solicitacaoDoacaoRepository.countSolicitacoesDoacaoAtivas();
    }

    public Long countSolicitacoesDoacaoAtivasByEntidadeAndTipoSanguineo(Long entidadeId, Long tipoSanguineoId){
        return solicitacaoDoacaoRepository.countSolicitacoesDoacaoAtivasByEntidadeAndTipoSanguineo(entidadeId, tipoSanguineoId);
    }

    @Transactional
    public Page<SolicitacaoDoacaoDTO> listAllByEntidadeId(Pageable pageable, Long id){
        validation.entidadeOrResourceNotFoundException(entidadeRepository, id);
        Page<SolicitacaoDoacaoDTO> solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                solicitacaoDoacaoRepository.findAllByEntidadeIdAndAtivoTrue(pageable, id));
        return solicitacoesDoacao;
    }

    public List<DoadorDTO> listAllDoadoresBySolicitacaoId(Long id){
        validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository, id);
        return doadorService.listAllBySolicitacaoId(id);
    }

    @Transactional
    public Page<SolicitacaoDoacaoDTO> listAllByEntidadeIdAndTiposSanguineosAndDescricao(
            Pageable pageable, Long id, String tipoSanguineo, String search){

        Page<SolicitacaoDoacaoDTO> solicitacoesDoacao;
        List<Long> tipoSanguineos;
        Entidade entidade = validation.entidadeOrResourceNotFoundException(entidadeRepository, id);

        if(tipoSanguineo != null && search == null){
            tipoSanguineos = utilidades.convertStringToList(tipoSanguineo);
            solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                    solicitacaoDoacaoRepository.findAllByEntidadeIdAndTiposSanguineos(pageable, tipoSanguineos, entidade.getId()));
        }else if(tipoSanguineo == null && search != null){
            solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                    solicitacaoDoacaoRepository.findAllByEntidadeIdAndDescricao(pageable, search, entidade.getId()));
        }else if(tipoSanguineo != null && search != null){
            tipoSanguineos = utilidades.convertStringToList(tipoSanguineo);
            solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                    solicitacaoDoacaoRepository.findAllByEntidadeIdAndTiposSanguineosAndDescricao(pageable, tipoSanguineos, search, entidade.getId()));
        }else {
            solicitacoesDoacao = SolicitacaoDoacaoDTO.converterMotelToDTO(
                    solicitacaoDoacaoRepository.findAllByEntidadeIdAndAtivoTrue(pageable, entidade.getId()));
        }

        return solicitacoesDoacao;

    }

    public SolicitacaoDoacaoDTO listById(Long id){
        SolicitacaoDoacao solicitacaoDoacao = validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository, id);
        return new SolicitacaoDoacaoDTO(solicitacaoDoacao);
    }

    public SolicitacaoDoacaoDTO update(SolicitacaoDoacaoFormUpdate solicitacaoDoacaoFormUpdate){

        Entidade entidade = entidadeRepository.findById(
                solicitacaoDoacaoFormUpdate.getIdEntidade()).get();

        Set<TipoSanguineo> tipoSanguineoSet = new HashSet<>();
        List<TipoSanguineo> tipoSanguineos = new ArrayList<>();
        List<Long> tipoSanguineoList = new ArrayList<>();

        for(Long tipoSanguineoId: solicitacaoDoacaoFormUpdate.getTiposSanguineos()){
            tipoSanguineoSet.add(validation.tipoSanguineoOrResourceNotFoundException(
                    tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineos.add(validation.tipoSanguineoOrResourceNotFoundException(
                    tipoSanguineoRepository, tipoSanguineoId));
            tipoSanguineoList.add(tipoSanguineoId);
        }

        SolicitacaoDoacao solicitacaoDoacao = validation.solicitacaoDoacaoOrResourceNotFoundException(
                solicitacaoDoacaoRepository, solicitacaoDoacaoFormUpdate.getId()
        );

        solicitacaoDoacao.setEntidade(entidade);
        solicitacaoDoacao.setTiposSanguineos(tipoSanguineos);
        solicitacaoDoacao.setDescricao(solicitacaoDoacaoFormUpdate.getDescricao());

        SolicitacaoDoacao solicitacaoDoacaoRegistered = solicitacaoDoacaoRepository.save(solicitacaoDoacao);

        return new SolicitacaoDoacaoDTO(solicitacaoDoacaoRegistered);

    }

    public void deleteById(Long id){
        SolicitacaoDoacao solicitacaoDoacao = validation.solicitacaoDoacaoOrResourceNotFoundException(solicitacaoDoacaoRepository, id);
        solicitacaoDoacao.setAtivo(false);
        solicitacaoDoacaoRepository.save(solicitacaoDoacao);
    }

}
