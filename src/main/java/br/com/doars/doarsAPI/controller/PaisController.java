package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.PaisDTO;
import br.com.doars.doarsAPI.service.PaisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/localidades/paises")
@Api(tags = "Pais (Paises)")
public class PaisController {

    private PaisService paisService;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de todos os paises cadastrados na nossa base de dados.")
    public ResponseEntity<List<PaisDTO>> listAll(){
        return ResponseEntity.status(HttpStatus.OK).body(paisService.listAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um pais através do identificador interno.")
    public ResponseEntity<PaisDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(paisService.listById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro de um pais através do identificador interno.")
    public ResponseEntity deleteById(@PathVariable Long id){
        paisService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
