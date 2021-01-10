package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import br.com.doars.doarsAPI.controller.dto.MunicipiosSimpleDTO;
import br.com.doars.doarsAPI.controller.form.DoadorForm;
import br.com.doars.doarsAPI.controller.form.DoadorFormUpdate;
import br.com.doars.doarsAPI.domain.*;
import br.com.doars.doarsAPI.repository.DoadorRepository;
import br.com.doars.doarsAPI.util.Validation;
import br.com.doars.doarsAPI.repository.EstadoRepository;
import br.com.doars.doarsAPI.repository.MunicipioRepository;
import br.com.doars.doarsAPI.repository.TipoSanguineoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoadorService {

    private DoadorRepository doadorRepository;
    private EstadoRepository estadoRepository;
    private MunicipioRepository municipioRepository;
    private TipoSanguineoRepository tipoSanguineoRepository;

    private EmailService emailService;
    private Validation validation;

    public DoadorDTO register(DoadorForm doadorForm){

        Municipios municipios = validation.municipioOrReourceNotFoundException(municipioRepository, doadorForm.getEndereco().getIdMunicipio());
        Estados estados = validation.estadoOrResourceNotFoundException(estadoRepository, doadorForm.getEndereco().getIdEstado());
        TipoSanguineo tipoSanguineo = validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, doadorForm.getIdTipoSanguineo());

        Endereco endereco = new Endereco(estados, municipios, doadorForm.getEndereco().getLogradouro(), doadorForm.getEndereco().getNumero());
        Contato contato = new Contato(doadorForm.getContato().getEmail(), doadorForm.getContato().getCelular(), doadorForm.getContato().getTelefone());

        Doador doador = new Doador();
        doador.setNome(doadorForm.getNome());
        doador.setSexo(doadorForm.getSexo());
        doador.setEndereco(endereco);
        doador.setContato(contato);
        doador.setTipoSanguineo(tipoSanguineo);

        validation.doadorOrExists(doadorRepository, doador);

        Doador doadorRegistered = doadorRepository.save(doador);

        emailService.sendEmailRegisterDoador(doadorRegistered);

        return new DoadorDTO(doadorRegistered);

    }

    public Page<DoadorDTO> listAll(Pageable pageable){
        Page<DoadorDTO> doadorDTOS = DoadorDTO.converterMotelToDTO(doadorRepository.findAll(pageable));
        return doadorDTOS;
    }

    public DoadorDTO listById(Long id){
        Doador doador = validation.doadorOrResourceNotFoundException(doadorRepository, id);
        return new DoadorDTO(doador);
    }

    public List<DoadorDTO> listByTipoSanguineo(Long idTipoSanguineo){

        validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, idTipoSanguineo);

        List<DoadorDTO> doadoresDTO = DoadorDTO.converterMotelToDTO(
                doadorRepository.findByTipoSanguineoId(idTipoSanguineo));

        return doadoresDTO;

    }

    public List<DoadorDTO> listAllByTipoSanguineoAndMunicipio(Long idTipoSanguineo, Long idMunicipio, Long distancia){

        Municipios municipioCentral = validation.municipioOrReourceNotFoundException(
                municipioRepository, idMunicipio);

        List<MunicipiosSimpleDTO> municipiosSimpleDTOS = MunicipiosSimpleDTO.converterMotelToDTO(
                municipioRepository.findAllMunicipiosNearby(municipioCentral.getLatitude(), municipioCentral.getLongitude() ,distancia)
        );

        List<Long> municipiosId = new ArrayList<>();


        for(MunicipiosSimpleDTO municipiosSimpleDTO: municipiosSimpleDTOS){
            municipiosId.add(municipiosSimpleDTO.getId());
        }

        List<DoadorDTO> doadoresDTO = DoadorDTO.converterMotelToDTO(
                doadorRepository.findByTipoSanguineoIdNearByEntidade(idTipoSanguineo, municipiosId)
        );

        return doadoresDTO;
    }

    public List<DoadorDTO> listAllByTipoSanguineoListAndMunicipio(List<Long> idTipoSanguineo, Long idMunicipio, Long distancia){

        Municipios municipioCentral = validation.municipioOrReourceNotFoundException(municipioRepository, idMunicipio);
        List<MunicipiosSimpleDTO> municipiosSimpleDTOS = MunicipiosSimpleDTO.converterMotelToDTO(
                municipioRepository.findAllMunicipiosNearby(municipioCentral.getLatitude(), municipioCentral.getLongitude() ,distancia));

        List<Long> municipiosId = new ArrayList<>();

        for(MunicipiosSimpleDTO municipiosSimpleDTO: municipiosSimpleDTOS){
            municipiosId.add(municipiosSimpleDTO.getId());
        }

        List<DoadorDTO> doadoresDTO = DoadorDTO.converterMotelToDTO(
                doadorRepository.findByTipoSanguineoIdListNearByEntidade(idTipoSanguineo, municipiosId)
        );

        return doadoresDTO;
    }

    public DoadorDTO update(DoadorFormUpdate doadorFormUpdate){

        Municipios municipios = validation.municipioOrReourceNotFoundException(
                municipioRepository, doadorFormUpdate.getEndereco().getIdMunicipio());

        Estados estados = validation.estadoOrResourceNotFoundException(
                estadoRepository, doadorFormUpdate.getEndereco().getIdEstado());

        TipoSanguineo tipoSanguineo = validation.tipoSanguineoOrResourceNotFoundException(
                tipoSanguineoRepository, doadorFormUpdate.getIdTipoSanguineo());

        Endereco endereco = new Endereco(estados, municipios, doadorFormUpdate.getEndereco().getLogradouro(), doadorFormUpdate.getEndereco().getNumero());
        Contato contato = new Contato(doadorFormUpdate.getContato().getEmail(), doadorFormUpdate.getContato().getCelular(), doadorFormUpdate.getContato().getTelefone());

        Doador doador = validation.doadorOrResourceNotFoundException(doadorRepository, doadorFormUpdate.getId());
        doador.setNome(doadorFormUpdate.getNome());
        doador.setSexo(doadorFormUpdate.getSexo());
        doador.setEndereco(endereco);
        doador.setContato(contato);
        doador.setTipoSanguineo(tipoSanguineo);

        validation.otherDoadorWithEmail(doadorRepository, doador, doadorFormUpdate.getId());

        Doador doadorRegistered = doadorRepository.save(doador);

        return new DoadorDTO(doadorRegistered);

    }

    public void deleteById(Long id){
        validation.doadorOrResourceNotFoundException(doadorRepository, id);
        doadorRepository.deleteById(id);
    }

}
