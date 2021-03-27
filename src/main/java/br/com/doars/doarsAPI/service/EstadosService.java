package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.EstadosDTO;
import br.com.doars.doarsAPI.exception.ViolationException;
import br.com.doars.doarsAPI.repository.EstadoRepository;
import br.com.doars.doarsAPI.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadosService {

    private EstadoRepository estadoRepository;
    private Validation validation;

    public List<EstadosDTO> listAll(){
        List<EstadosDTO> estadosDTOS = EstadosDTO.converterMotelToDTO(estadoRepository.findAllOrderByNome(Sort.by("nome")));
        return estadosDTOS;
    }

    public EstadosDTO listBySigla(String sigla){

        EstadosDTO estadosDTO = new EstadosDTO(
                validation.estadoOrResourceNotFoundException(estadoRepository, sigla));

        return estadosDTO;

    }

    public EstadosDTO listById(Long id){

        EstadosDTO estadosDTO = new EstadosDTO(
                validation.estadoOrResourceNotFoundException(estadoRepository, id));

        return estadosDTO;

    }

    public void deleteById(Long id){
        validation.estadoOrResourceNotFoundException(estadoRepository, id);

        try {

            estadoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){

            throw new ViolationException();

        }

    }

}
