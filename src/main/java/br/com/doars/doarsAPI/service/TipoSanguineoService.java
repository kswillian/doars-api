package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.dto.TipoSanguineoDTO;
import br.com.doars.doarsAPI.controller.form.TipoSanguineoForm;
import br.com.doars.doarsAPI.controller.form.TipoSanguineoFormUpdate;
import br.com.doars.doarsAPI.exception.ViolationException;
import br.com.doars.doarsAPI.domain.TipoSanguineo;
import br.com.doars.doarsAPI.repository.TipoSanguineoRepository;
import br.com.doars.doarsAPI.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoSanguineoService {

    private TipoSanguineoRepository tipoSanguineoRepository;
    private Validation validation;

    public TipoSanguineoDTO register(TipoSanguineoForm tipoSanguineoForm){
        TipoSanguineo tipoSanguineo = new TipoSanguineo(tipoSanguineoForm.getDescricao());
        TipoSanguineo tipoSanguineoRegistered = tipoSanguineoRepository.save(tipoSanguineo);
        return new TipoSanguineoDTO(tipoSanguineoRegistered);
    }

    public List<TipoSanguineoDTO> listAll(){
        List<TipoSanguineoDTO> tipoSanguineosDTO = TipoSanguineoDTO.converterMotelToDTO(tipoSanguineoRepository.findAll());
        return tipoSanguineosDTO;
    }

    public TipoSanguineoDTO listById(Long id){
        TipoSanguineo tipoSanguineo = validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, id);
        return new TipoSanguineoDTO(tipoSanguineo);
    }

    public TipoSanguineoDTO  update(TipoSanguineoFormUpdate tipoSanguineoForm){
        TipoSanguineo tipoSanguineo = validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, tipoSanguineoForm.getId());
        tipoSanguineo.setDescricao(tipoSanguineoForm.getDescricao());
        TipoSanguineo tipoSanguineoRegistered = tipoSanguineoRepository.save(tipoSanguineo);
        return new TipoSanguineoDTO(tipoSanguineoRegistered);
    }

    public void deleteById(Long id){

        validation.tipoSanguineoOrResourceNotFoundException(tipoSanguineoRepository, id);

        try {

            tipoSanguineoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){

            throw new ViolationException();

        }

    }

}
