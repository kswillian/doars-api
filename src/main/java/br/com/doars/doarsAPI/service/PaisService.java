package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.PaisDTO;
import br.com.doars.doarsAPI.exception.ViolationException;
import br.com.doars.doarsAPI.repository.PaisRepository;
import br.com.doars.doarsAPI.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaisService {

    private PaisRepository paisRepository;
    private Validation validation;

    public List<PaisDTO> listAll(){
        List<PaisDTO> paisDTOS = PaisDTO.converterMotelToDTO(paisRepository.findAll());
        return paisDTOS;
    }

    public PaisDTO listById(Long id){
        PaisDTO paisDTO = new PaisDTO(validation.paisOrResourceNotFoundException(paisRepository, id));
        return paisDTO;
    }

    public void deleteById(Long id){
        validation.paisOrResourceNotFoundException(paisRepository, id);

        try {

            paisRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){

            throw new ViolationException();

        }

    }

}
