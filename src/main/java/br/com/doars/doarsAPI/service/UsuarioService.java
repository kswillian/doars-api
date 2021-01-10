package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.controller.form.RecuperacaoSenhaUsuarioForm;
import br.com.doars.doarsAPI.repository.DoadorRepository;
import br.com.doars.doarsAPI.util.Utilidades;
import br.com.doars.doarsAPI.util.Validation;
import br.com.doars.doarsAPI.domain.Perfil;
import br.com.doars.doarsAPI.domain.Usuario;
import br.com.doars.doarsAPI.controller.form.UsuarioForm;
import br.com.doars.doarsAPI.repository.EntidadeRepository;
import br.com.doars.doarsAPI.repository.PerfilRepository;
import br.com.doars.doarsAPI.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private PerfilRepository perfilRepository;
    private UsuarioRepository usuarioRepository;

    private EntidadeRepository entidadeRepository;
    private DoadorRepository doadorRepository;

    private Utilidades utilidades;
    private Validation validation;

    public Usuario register(UsuarioForm usuarioForm, Long idResource){

        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioForm.getEmail());
        usuario.setSenha(utilidades.generateBCrypt(usuarioForm.getSenha()));
        usuario.setCodigoValidacao(String.valueOf(utilidades.generateValidationCode()));
        usuario.setPerfils(findPerfilsByList(usuarioForm.getPerfil()));
        usuario.setIsCredentialsNonExpired(true);
        usuario.setIsNonExpired(true);
        usuario.setIsNonLocked(true);
        usuario.setIsEnabled(false);
        addVinculoEntidadeDoador(usuario, idResource);
        return usuarioRepository.save(usuario);

    }

    public Usuario update(UsuarioForm usuarioForm, Long idResource){

        Usuario usuario = usuarioRepository.findByEmail(usuarioForm.getEmail()).get();

        usuario.setEmail(usuarioForm.getEmail());
        usuario.setCodigoValidacao(String.valueOf(utilidades.generateValidationCode()));
        usuario.setIsEnabled(false);

        return usuarioRepository.save(usuario);

    }

    public void setNewSenha(RecuperacaoSenhaUsuarioForm usuarioForm){

        Usuario usuario = usuarioRepository.findByEmail(usuarioForm.getEmail()).get();
        usuario.setSenha(usuarioForm.getNovaSenha());
        usuario.setDataAlteracaoSenha(LocalDateTime.now());
        usuarioRepository.save(usuario);

    }

    public void addVinculoEntidadeDoador(Usuario usuario, Long idResource){
        for(Perfil perfil: usuario.getPerfils()){

            if(perfil.getDescricao().equalsIgnoreCase("Entidade")){

                usuario.setEntidade(entidadeRepository.findById(idResource).get());

            }else if(perfil.getDescricao().equalsIgnoreCase("Doador")){

                usuario.setDoador(doadorRepository.findById(idResource).get());

            }

        }
    }

    public List<Perfil> findPerfilsByList(String perfilDescricao){

        List<Perfil> perfils = new ArrayList<>();
        Perfil perfil = perfilRepository.findByDescricao(perfilDescricao);
        perfils.add(perfil);
        return perfils;

    }

    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    public void activateUsuario(String email){
        validation.usuarioByEmailOrResourceNotFoundException(usuarioRepository, email);
    }

    public void deactivateUsuario(String email){
        validation.usuarioByEmailOrResourceNotFoundException(usuarioRepository, email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(s);

        if(usuario.isPresent()){
            return usuario.get();
        }

        throw new UsernameNotFoundException("Dados inválidos!");

    }
}
