package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Endereco;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnderecoDTO {

    private EstadosDTO estados;

    private MunicipiosSimpleDTO municipios;

    @ApiModelProperty(value = "Logradouro do endereço.")
    private String logradouro;

    @ApiModelProperty(value = "Número do endereço.")
    private String numero;

    public EnderecoDTO(Endereco endereco) {
        this.estados = new EstadosDTO(endereco.getEstados());
        this.municipios = new MunicipiosSimpleDTO(endereco.getMunicipios());
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
    }

}
