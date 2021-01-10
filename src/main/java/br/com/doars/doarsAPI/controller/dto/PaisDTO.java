package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Pais;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PaisDTO {

    @ApiModelProperty(value = "Identificador interno do pais.")
    private Long id;

    @ApiModelProperty(value = "Nome do pais.")
    private String nome;

    @ApiModelProperty(value = "Sigla do pais.")
    private String sigla;

    public PaisDTO(Pais pais) {
        this.id = pais.getId();
        this.nome = pais.getNome();
        this.sigla = pais.getSigla();
    }

    public static List<PaisDTO> converterMotelToDTO(List<Pais> paisList){
        return paisList.stream().map(PaisDTO::new).collect(Collectors.toList());
    }

}
