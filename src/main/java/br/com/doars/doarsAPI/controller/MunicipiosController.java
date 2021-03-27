package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.MunicipiosDTO;
import br.com.doars.doarsAPI.controller.dto.MunicipiosSimpleDTO;
import br.com.doars.doarsAPI.service.MunicipiosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/localidades/municipios")
@Api(tags = "Municipio (Municipios)")
public class MunicipiosController {

    private MunicipiosService municipiosService;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista páginada de todos os municipios da Federação.")
    public ResponseEntity<Page<MunicipiosDTO>> listAll(
            @RequestParam (required = false, defaultValue = "") String search,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        if(search.length() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(municipiosService.listAll(pageable, search));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(municipiosService.listAll(pageable));
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um municipio através do identificador (Cód. IBGE).")
    public ResponseEntity<MunicipiosDTO> listById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(municipiosService.listById(id));
    }

    @GetMapping("/estados/{sigla}")
    @ApiOperation(value = "Retorna uma lista de municipios através da sigla de um estado da Federação.")
    public ResponseEntity<Page<MunicipiosDTO>> listAllByEstado(
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable String sigla){
        return ResponseEntity.status(HttpStatus.OK).body(municipiosService.listAllByEstadoSigla(pageable, sigla));
    }

    @GetMapping("{id}/proximidades")
    @ApiOperation(value = "Retorna uma lista de municipios em um raio de 30Km com base em um municipio central.")
    public ResponseEntity<List<MunicipiosSimpleDTO>> listAllNearBy(
            @RequestParam( value = "distancia", required = false, defaultValue = "30") Long distancia, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(municipiosService.listAllMunicipiosNearBy(id, distancia));
    }

}
