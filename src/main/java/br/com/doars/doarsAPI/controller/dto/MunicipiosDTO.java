package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Municipios;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class MunicipiosDTO {

    @ApiModelProperty(value = "Identificador interno do municipio.")
    private Long id;

    @ApiModelProperty(value = "Nome do municipio.")
    private String nome;

    private EstadosDTO estado;

    @ApiModelProperty(value = "Latitude do municipio.")
    private BigDecimal latitude;

    @ApiModelProperty(value = "Longitude do municipio.")
    private BigDecimal longitude;

    @ApiModelProperty(value = "Informa se o municipio é capital do seu estado.")
    private Boolean capital;

    public MunicipiosDTO(Municipios municipios) {
        this.id = municipios.getId();
        this.nome = municipios.getNome();
        this.estado = new EstadosDTO(municipios.getEstados());
        this.latitude = municipios.getLatitude();
        this.longitude = municipios.getLongitude();
        this.capital = municipios.getCapital();
    }

    public static List<MunicipiosDTO> converterMotelToDTO(List<Municipios> municipios){
        return municipios.stream().map(MunicipiosDTO::new).collect(Collectors.toList());
    }

    public static Page<MunicipiosDTO> converterMotelToDTO(Page<Municipios> municipiosDTOPage){
        return municipiosDTOPage.map(MunicipiosDTO::new);
    }

}
