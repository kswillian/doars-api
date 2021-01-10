package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.form.PerfilForm;
import br.com.doars.doarsAPI.domain.Perfil;
import br.com.doars.doarsAPI.repository.PerfilRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PerfilService {

    private PerfilRepository perfilRepository;

    private Perfil register(PerfilForm perfilForm){

        Perfil perfil = new Perfil(perfilForm.getDescricao());
        return perfilRepository.save(perfil);

    }

    public List<Perfil> listAll(){
        return perfilRepository.findAll();
    }

    public Perfil listById(Long id){
        return perfilRepository.findById(id).get();
    }

}
