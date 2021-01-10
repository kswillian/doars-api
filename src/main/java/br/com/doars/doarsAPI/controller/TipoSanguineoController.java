package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.TipoSanguineoDTO;
import br.com.doars.doarsAPI.controller.form.TipoSanguineoForm;
import br.com.doars.doarsAPI.controller.form.TipoSanguineoFormUpdate;
import br.com.doars.doarsAPI.service.TipoSanguineoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/tipos-sanguineos")
@Api(tags = "Tipo Sanguineo (Tipos Sanguineos)")
public class TipoSanguineoController {

    private TipoSanguineoService tipoSanguineoService;

    @PostMapping
    @ApiOperation(value = "Registra uma novo tipo sanguineo em nossa base de dados.")
    public ResponseEntity<TipoSanguineoDTO> register(@RequestBody @Valid TipoSanguineoForm tipoSanguineoForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoSanguineoService.register(tipoSanguineoForm));
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de todos os tipos sanguineos de nossa base de dados.")
    public ResponseEntity<List<TipoSanguineoDTO>> listAll(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoSanguineoService.listAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna o tipo sanguineo pelo o seu identificador interno.")
    public ResponseEntity<TipoSanguineoDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(tipoSanguineoService.listById(id));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza o tipo sanguineo em nossa base de dados.")
    public ResponseEntity<TipoSanguineoDTO> update(@RequestBody @Valid TipoSanguineoFormUpdate tipoSanguineoFormUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(tipoSanguineoService.update(tipoSanguineoFormUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro do tipo sanguineo da nossa base de dados.")
    public ResponseEntity deleteById(@PathVariable Long id){
        tipoSanguineoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
