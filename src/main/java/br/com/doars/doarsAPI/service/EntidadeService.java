package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.form.EntidadeForm;
import br.com.doars.doarsAPI.domain.*;
import br.com.doars.doarsAPI.exception.ViolationException;
import br.com.doars.doarsAPI.util.Validation;
import br.com.doars.doarsAPI.controller.dto.EntidadeDTO;
import br.com.doars.doarsAPI.controller.form.EntidadeFormUpdate;
import br.com.doars.doarsAPI.controller.form.UsuarioForm;
import br.com.doars.doarsAPI.repository.EntidadeRepository;
import br.com.doars.doarsAPI.repository.EstadoRepository;
import br.com.doars.doarsAPI.repository.MunicipioRepository;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntidadeService {

    private EntidadeRepository entidadeRepository;
    private MunicipioRepository municipioRepository;
    private EstadoRepository estadoRepository;

    private UsuarioRepository usuarioRepository;

    private Validation validation;
    private EmailService emailService;

    private UsuarioService usuarioService;

    public EntidadeDTO register(EntidadeForm entidadeForm){

        Municipios municipios = validation.municipioOrReourceNotFoundException(municipioRepository, entidadeForm.getEndereco().getIdMunicipio());
        Estados estados = validation.estadoOrResourceNotFoundException(estadoRepository, entidadeForm.getEndereco().getIdEstado());

        Endereco endereco = new Endereco(estados, municipios, entidadeForm.getEndereco().getLogradouro(), entidadeForm.getEndereco().getNumero());
        Contato contato = new Contato(entidadeForm.getContato().getEmail(), entidadeForm.getContato().getCelular(), entidadeForm.getContato().getTelefone());

        Entidade entidade = new Entidade();
        entidade.setNome(entidadeForm.getNome());
        entidade.setCnpj(entidadeForm.getCnpj());
        entidade.setDescricao(entidadeForm.getDescricao());
        entidade.setDiasSemanaList(entidadeForm.getDiasSemanaList());
        entidade.setHoraInicialFuncionamento(entidadeForm.getHoraInicialFuncionamento());
        entidade.setHoraFinalFuncionamento(entidadeForm.getHoraFinalFuncionamento());
        entidade.setTipoEntidade(entidadeForm.getTipoEntidade());
        entidade.setEndereco(endereco);
        entidade.setContato(contato);

        validation.entidadeOrCnpExists(entidadeRepository, entidade);
        validation.entidadeOrEmailExists(entidadeRepository, entidade);

        Entidade entidadeRegistered = entidadeRepository.save(entidade);

        Usuario usuarioRegistered = usuarioService.register(new UsuarioForm(entidadeForm.getUsuario().getEmail(),
                entidadeForm.getUsuario().getSenha(), entidadeForm.getUsuario().getPerfil()), entidade.getId());

        if(entidadeRegistered.getId() != null){
            emailService.sendEmailRegisterEntidade(entidadeRegistered, usuarioRegistered);
        }

        return new EntidadeDTO(entidadeRegistered);

    }

    public Page<EntidadeDTO> listAll(Pageable pageable){
        Page<EntidadeDTO> entidadesDTO = EntidadeDTO.converterMotelToDTO(entidadeRepository.findAll(pageable));
        return entidadesDTO;
    }

    public EntidadeDTO listById(Long id){
        EntidadeDTO entidadeDTO = new EntidadeDTO(validation.entidadeOrResourceNotFoundException(entidadeRepository, id));
        return entidadeDTO;
    }

    @Transactional
    public EntidadeDTO listByEmail(String email){
        EntidadeDTO entidadeDTO = new EntidadeDTO(
                validation.entidadeOrResourceNotFoundException(entidadeRepository, email));
        return entidadeDTO;
    }

    public EntidadeDTO update(EntidadeFormUpdate entidadeFormUpdate){

        Municipios municipios = validation.municipioOrReourceNotFoundException(
                municipioRepository, entidadeFormUpdate.getEndereco().getIdMunicipio());

        Estados estados = validation.estadoOrResourceNotFoundException(
                estadoRepository, entidadeFormUpdate.getEndereco().getIdEstado());

        Endereco endereco = new Endereco(estados, municipios, entidadeFormUpdate.getEndereco().getLogradouro(), entidadeFormUpdate.getEndereco().getNumero());
        Contato contato = new Contato(entidadeFormUpdate.getContato().getEmail(), entidadeFormUpdate.getContato().getCelular(), entidadeFormUpdate.getContato().getTelefone());

        Entidade entidade = validation.entidadeOrResourceNotFoundException(entidadeRepository, entidadeFormUpdate.getId());
        entidade.setNome(entidadeFormUpdate.getNome());
        entidade.setCnpj(entidadeFormUpdate.getCnpj());
        entidade.setTipoEntidade(entidadeFormUpdate.getTipoEntidade());
        entidade.setDescricao(entidadeFormUpdate.getDescricao());
        entidade.setDiasSemanaList(entidadeFormUpdate.getDiasSemanaList());
        entidade.setHoraInicialFuncionamento(entidadeFormUpdate.getHoraInicialFuncionamento());
        entidade.setHoraFinalFuncionamento(entidadeFormUpdate.getHoraFinalFuncionamento());
        entidade.setEndereco(endereco);
        entidade.setContato(contato);

        validation.entidadeOrCnpExists(entidadeRepository, entidade);
        validation.entidadeOrEmailExists(entidadeRepository, entidade);

        Usuario usuarioRegistered = usuarioService.update(new UsuarioForm(entidadeFormUpdate.getUsuario().getEmail(), entidadeFormUpdate.getUsuario().getSenha(),
                entidadeFormUpdate.getUsuario().getPerfil()), entidadeFormUpdate.getId());

        Entidade entidadeRegistered = entidadeRepository.save(entidade);

        if(entidadeRegistered.getId() != null){
            emailService.sendEmailUpdateEntidade(entidadeRegistered, usuarioRegistered);
        }

        return new EntidadeDTO(entidadeRegistered);

    }

    public void deleteById(Long id){

        Entidade entidade = validation.entidadeOrResourceNotFoundException(entidadeRepository, id);
        Optional<Usuario> usuario = usuarioRepository.findByEntidadeId(entidade.getId());

        try {

            if(usuario.isPresent()){
                usuarioRepository.deleteById(usuario.get().getId());
                entidadeRepository.deleteById(id);
            }

        }catch (DataIntegrityViolationException e){
            throw new ViolationException();
        }

    }

}
