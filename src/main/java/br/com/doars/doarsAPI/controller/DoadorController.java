package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import br.com.doars.doarsAPI.controller.form.DoadorForm;
import br.com.doars.doarsAPI.controller.form.DoadorFormUpdate;
import br.com.doars.doarsAPI.service.DoadorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/doadores")
@Api(tags = "Doador (Doadores)")
public class DoadorController {

    private DoadorService doadorService;

    @PostMapping
    @ApiOperation(value = "Registra um novo doador em nossa base de dados.")
    public ResponseEntity<DoadorDTO> register(@RequestBody @Valid DoadorForm doadorForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(doadorService.register(doadorForm));
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista páginada de todos os doadores de nossa base de dados.")
    public ResponseEntity<Page<DoadorDTO>> listAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(doadorService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna o doador pelo o seu identificador interno.")
    public ResponseEntity<DoadorDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(doadorService.listById(id));
    }

    @GetMapping("/tipoSanguineo/{id}")
    @ApiOperation(value = "Retorna uma lista de todos os doadores de nossa base de dados com base no tipo sanguineo informado.")
    public ResponseEntity<List<DoadorDTO>> listByTipoSanguineo(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(doadorService.listByTipoSanguineo(id));
    }

    @GetMapping("/tipoSanguineo/{id}/municipio/{idMunicipio}")
    @ApiOperation(value = "Retorna uma lista de doadores nas proximidades de um municipio e pelo tipo sanguineo.")
    public ResponseEntity<List<DoadorDTO>> listByTipoSanguineo(
            @RequestParam( value = "distancia", required = false, defaultValue = "30") Long distancia,
            @PathVariable Long id, @PathVariable Long idMunicipio){
        return ResponseEntity.status(HttpStatus.OK).body(doadorService.listAllByTipoSanguineoAndMunicipio(id, idMunicipio, distancia));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza os dados do doador em nossa base de dados.")
    public ResponseEntity<DoadorDTO> update(@RequestBody @Valid DoadorFormUpdate doadorFormUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(doadorService.update(doadorFormUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro do doador da nossa base de dados.")
    public ResponseEntity deleteById(@PathVariable Long id){
        doadorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
