package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.form.EntidadeForm;
import br.com.doars.doarsAPI.controller.dto.EntidadeDTO;
import br.com.doars.doarsAPI.controller.form.EntidadeFormUpdate;
import br.com.doars.doarsAPI.service.EntidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/entidades")
@Api(tags = "Entidade (Entidades)")
public class EntidadeController {

    private EntidadeService entidadeService;

    @PostMapping
    @ApiOperation(value = "Registra uma nova entidade em nossa base de dados.")
    public ResponseEntity<EntidadeDTO> register(@RequestBody @Valid EntidadeForm entidadeForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(entidadeService.register(entidadeForm));
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista páginada de todos as entidades de nossa base de dados.")
    public ResponseEntity<Page<EntidadeDTO>> listAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(entidadeService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna a entidade pelo o seu identificador interno.")
    public ResponseEntity<EntidadeDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(entidadeService.listById(id));
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Retorna a entidade pelo o seu email de cadastro.")
    public ResponseEntity<EntidadeDTO> listByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(entidadeService.listByEmail(email));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza os dados da entidade em nossa base de dados.")
    public ResponseEntity<EntidadeDTO> update(@RequestBody @Valid EntidadeFormUpdate entidadeFormUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(entidadeService.update(entidadeFormUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro da entidade da nossa base de dados.")
    public ResponseEntity deleteById(@PathVariable Long id){
        entidadeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
