package br.com.doars.doarsAPI.util;

import br.com.doars.doarsAPI.domain.*;
import br.com.doars.doarsAPI.exception.DoadorAlreadyExists;
import br.com.doars.doarsAPI.exception.EntidadeCNPJAlreadyExists;
import br.com.doars.doarsAPI.exception.ExceptionDetails;
import br.com.doars.doarsAPI.exception.ResourceNotFoundException;
import br.com.doars.doarsAPI.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Validation {

    private Utilidades utilidades;

    public String dataIntegrityViolationException() throws JsonProcessingException {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .titulo("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .detalhes("br.com.doemaisrs.doemaisrsAPI.ViolationException")
                .mensagem("Este item não pode ser excluído pois está vinculado a outro(s) registro(s)")
                .build();

        String response = utilidades.convertObjectToJson(exceptionDetails);
        return response;

    }

    public String tokenExpiredException(ExpiredJwtException e) throws JsonProcessingException {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .titulo("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .detalhes(e.getClass().getName())
                .mensagem("Token expirado")
                .build();

        String response = utilidades.convertObjectToJson(exceptionDetails);
        return response;

    }

    public String tokenSignatureException(Exception e) throws JsonProcessingException {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .titulo("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .detalhes(e.getClass().getName())
                .mensagem("Token invalido")
                .build();

        String response = utilidades.convertObjectToJson(exceptionDetails);
        return response;

    }

    public String tokenSignatureException(SignatureException e) throws JsonProcessingException {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .titulo("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .detalhes(e.getClass().getName())
                .mensagem("Token invalido")
                .build();

        String response = utilidades.convertObjectToJson(exceptionDetails);
        return response;

    }

    public Usuario usuarioOrResourceNotFoundException(UsuarioRepository usuarioRepository, String codigo){
        return usuarioRepository.findByCodigoValidacao(codigo).orElseThrow(() -> new ResourceNotFoundException(String.format("O código de validação se encontra inválido.")));
    }

    public Usuario usuarioByEmailOrResourceNotFoundException(UsuarioRepository usuarioRepository, String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format("Não encontramos nenhum usuário com o email '%s' cadastrado em nossa base.", email)));
    }

    public Entidade entidadeOrResourceNotFoundException(EntidadeRepository entidadeRepository, Long id ){
        return entidadeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Entidade com id '%s' não foi encontrada.", id)));
    }

    public Entidade entidadeOrResourceNotFoundException(EntidadeRepository entidadeRepository, String email ){
        return entidadeRepository.findByContatoEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format("Entidade com o email '%s' não foi encontrada.", email)));
    }

    public void entidadeOrEmailExists(EntidadeRepository entidadeRepository, Entidade entidade){

        if(entidade.getId() != null){
            Entidade entidadeData = entidadeRepository.findById(entidade.getId()).get();
            if(entidadeRepository.existsByContatoEmail(entidade.getContato().getEmail()) && entidadeData.getId() != entidade.getId()){
                throw new EntidadeCNPJAlreadyExists(String.format("Já há uma entidade cadastrada com o email %s", entidade.getContato().getEmail()));
            }
        }else{
            if(entidadeRepository.existsByContatoEmail(entidade.getContato().getEmail())){
                throw new EntidadeCNPJAlreadyExists(String.format("Já há uma entidade cadastrada com o email %s", entidade.getContato().getEmail()));
            }
        }

    }

    public void entidadeOrCnpExists(EntidadeRepository entidadeRepository, Entidade entidade){

        if(entidade.getId() != null){
            Entidade entidadeData = entidadeRepository.findById(entidade.getId()).get();
            if(entidadeRepository.existsByCnpj(entidade.getCnpj()) && entidadeData.getId() != entidade.getId()){
                throw new EntidadeCNPJAlreadyExists(String.format("Já há uma entidade cadastrada com o CNPJ %s", entidade.getCnpj()));
            }
        }else{
            if(entidadeRepository.existsByCnpj(entidade.getCnpj())){
                throw new EntidadeCNPJAlreadyExists(String.format("Já há uma entidade cadastrada com o CNPJ %s", entidade.getCnpj()));
            }
        }

    }

    public void doadorOrExists(DoadorRepository doadorRepository, Doador doador){
        if(doadorRepository.existsByContatoEmail(doador.getContato().getEmail())){
            throw new DoadorAlreadyExists(String.format("Já há um doador cadastrada com o email %s", doador.getContato().getEmail()));
        }
    }

    public void otherDoadorWithEmail(
            DoadorRepository doadorRepository,
            Doador doador, Long id){

        if(doadorRepository.existsByContatoEmail(doador.getContato().getEmail())){

            Doador doadorUpdated = doadorRepository.findById(doador.getId()).get();

            if(doadorUpdated.getId() != id){
                throw new DoadorAlreadyExists(String.format("Já há um doador cadastrada com o email %s", doador.getContato().getEmail()));
            }

        }
    }

    public Doador doadorOrResourceNotFoundException(DoadorRepository doadorRepository, Long id){
        return doadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Doador com id '%s' não foi encontrado.", id)));
    }

    public TipoSanguineo tipoSanguineoOrResourceNotFoundException(TipoSanguineoRepository tipoSanguineoRepository, Long id){
        return tipoSanguineoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Tipo Sanguineo com id '%s' não foi encontrado.", id)));
    }

    public SolicitacaoDoacao solicitacaoDoacaoOrResourceNotFoundException(SolicitacaoDoacaoRepository solicitacaoDoacaoRepository, Long id){
        return solicitacaoDoacaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Registro de solicitação com id '%s' não foi encontrado.", id)));
    }

    public Pais paisOrResourceNotFoundException(PaisRepository paisRepository, Long id){
        return paisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Pais com id '%s' não foi encontrado.", id)));
    }

    public Municipios municipioOrReourceNotFoundException(MunicipioRepository municipioRepository, Long id){
        return municipioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Municipio com id '%s' não foi encontrado.", id)));
    }

    public Estados estadoOrResourceNotFoundException(EstadoRepository estadoRepository, Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Estado com id '%s' não foi encontrado.", id)));
    }

    public Estados estadoOrResourceNotFoundException(EstadoRepository estadoRepository, String sigla){
        return estadoRepository.findBySigla(sigla).orElseThrow(() -> new ResourceNotFoundException(String.format("Estado com a sigla '%s' não foi encontrado.", sigla)));
    }

}
