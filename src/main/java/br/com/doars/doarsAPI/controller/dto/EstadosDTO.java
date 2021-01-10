package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Estados;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EstadosDTO {

    @ApiModelProperty(value = "Identificador da Unidade da Federação.")
    private Long id;

    @ApiModelProperty(value = "Nome do Unidade da Federação.")
    private String nome;

    @ApiModelProperty(value = "Sigla do Unidade da Federação.")
    private String sigla;

    public EstadosDTO(Estados estados) {
        this.id = estados.getId();
        this.nome = estados.getNome();
        this.sigla = estados.getSigla();
    }

    public static List<EstadosDTO> converterMotelToDTO(List<Estados> estadosList){
        return estadosList.stream().map(EstadosDTO::new).collect(Collectors.toList());
    }

}
