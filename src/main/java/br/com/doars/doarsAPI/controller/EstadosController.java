package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.EstadosDTO;
import br.com.doars.doarsAPI.service.EstadosService;
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
@RequestMapping("api/v1/localidades/estados")
@Api(tags = "Estado (Estados)")
public class EstadosController {

    private EstadosService estadosService;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de todos os estados da Federação.")
    public ResponseEntity<List<EstadosDTO>> listAll(){
        return ResponseEntity.status(HttpStatus.OK).body(estadosService.listAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um estado através do identificador da Unidade da Federação.")
    public ResponseEntity<EstadosDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(estadosService.listById(id));
    }

    @GetMapping("/sigla/{sigla}")
    @ApiOperation(value = "Retorna um estado através de sua sigla.")
    public ResponseEntity<EstadosDTO> listBySigla(@PathVariable String sigla){
        return ResponseEntity.status(HttpStatus.OK).body(estadosService.listBySigla(sigla));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o registro de um estado através do identificador da Unidade da Federação.")
    public ResponseEntity deleteById(@PathVariable Long id){
        estadosService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
