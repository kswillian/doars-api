package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.MunicipiosDTO;
import br.com.doars.doarsAPI.controller.dto.MunicipiosSimpleDTO;
import br.com.doars.doarsAPI.exception.ViolationException;
import br.com.doars.doarsAPI.domain.Estados;
import br.com.doars.doarsAPI.domain.Municipios;
import br.com.doars.doarsAPI.repository.EstadoRepository;
import br.com.doars.doarsAPI.repository.MunicipioRepository;
import br.com.doars.doarsAPI.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MunicipiosService {

    private MunicipioRepository municipioRepository;
    private EstadoRepository estadoRepository;
    private Validation validation;

    public Page<MunicipiosDTO> listAll(Pageable pageable){
        Page<MunicipiosDTO> municipiosDTOS = MunicipiosDTO.converterMotelToDTO(municipioRepository.findAll(pageable));
       return municipiosDTOS;
    }

    public MunicipiosDTO listById(Long id){

        MunicipiosDTO municipiosDTO = new MunicipiosDTO(
                validation.municipioOrReourceNotFoundException(municipioRepository, id));

        return municipiosDTO;

    }

    public Page<MunicipiosDTO> listAllByEstadoSigla(Pageable pageable, String sigla){

        Estados estados = validation.estadoOrResourceNotFoundException(estadoRepository, sigla);
        Page<MunicipiosDTO> municipiosDTOS = MunicipiosDTO.converterMotelToDTO(municipioRepository.findAllByEstados(pageable, estados));

        return municipiosDTOS;

    }

    public List<MunicipiosSimpleDTO> listAllMunicipiosNearBy(Long id, Long distancia){

        Municipios municipioCentral = validation.municipioOrReourceNotFoundException(municipioRepository, id);

        List<MunicipiosSimpleDTO> municipiosSimpleDTOS = MunicipiosSimpleDTO.converterMotelToDTO(
                municipioRepository.findAllMunicipiosNearby(
                        municipioCentral.getLatitude(),
                        municipioCentral.getLongitude(),
                        distancia
                )
        );

        return municipiosSimpleDTOS;

    }

    public void deleteById(Long id){
        validation.estadoOrResourceNotFoundException(estadoRepository, id);

        try {

            municipioRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){

            throw new ViolationException();

        }

    }

}
