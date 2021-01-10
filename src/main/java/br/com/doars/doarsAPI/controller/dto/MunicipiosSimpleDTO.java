package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Municipios;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MunicipiosSimpleDTO {

    @ApiModelProperty(value = "Identificador interno do municipio.")
    private Long id;

    @ApiModelProperty(value = "Nome do municipio.")
    private String nome;

    @ApiModelProperty(value = "Latitude do municipio.")
    private BigDecimal latitude;

    @ApiModelProperty(value = "Longitude do municipio.")
    private BigDecimal longitude;

    public MunicipiosSimpleDTO(Municipios municipios) {
        this.id = municipios.getId();
        this.nome = municipios.getNome();
        this.latitude = municipios.getLatitude();
        this.longitude = municipios.getLongitude();
    }

    public static List<MunicipiosSimpleDTO> converterMotelToDTO(List<Municipios> municipios){
        return municipios.stream().map(MunicipiosSimpleDTO::new).collect(Collectors.toList());
    }

}
