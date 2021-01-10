package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.SolicitacaoDoacaoDTO;
import br.com.doars.doarsAPI.controller.form.SolicitacaoDoacaoForm;
import br.com.doars.doarsAPI.controller.form.SolicitacaoDoacaoFormUpdate;
import br.com.doars.doarsAPI.service.SolicitacaoDoacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/notificacoes/doacoes")
@Api(tags = "Solicitação (Solicitações de Doação)")
public class SolicitacaoDoacaoController {

    private SolicitacaoDoacaoService solicitacaoDoacaoService;

    @PostMapping
    @ApiOperation(value = "Registra uma nova solicitação de doação de sangue em nossa base de dados.")
    public ResponseEntity<SolicitacaoDoacaoDTO> register(@RequestBody @Valid SolicitacaoDoacaoForm solicitacaoDoacaoForm){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(solicitacaoDoacaoService.register(solicitacaoDoacaoForm));
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista páginada de todas as solicitações de sangue de nossa base de dados.")
    public ResponseEntity<Page<SolicitacaoDoacaoDTO>> listAll(Pageable pageable, @RequestParam( value = "entidadeId", required = false) Long id){
        if(id != null){
            return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDoacaoService.listAllByEntidadeId(pageable, id));
        }
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDoacaoService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna a solicitação pelo o seu identificador interno.")
    public ResponseEntity<SolicitacaoDoacaoDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDoacaoService.listById(id));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza os dados de uma nova solicitação de doação de sangue em nossa base de dados.")
    public ResponseEntity<SolicitacaoDoacaoDTO> update(@RequestBody @Valid SolicitacaoDoacaoFormUpdate solicitacaoDoacaoFormUpdate){
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoDoacaoService.update(solicitacaoDoacaoFormUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro de solicitação de doação da nossa base de dados.")
    public ResponseEntity deleteById(@PathVariable Long id){
        solicitacaoDoacaoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
