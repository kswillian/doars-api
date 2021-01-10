package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnderecoForm {

    @NotNull
    @ApiModelProperty(value = "Id do Estado.")
    private Long idEstado;

    @NotNull
    @ApiModelProperty(value = "Id do Municipio.")
    private Long idMunicipio;

    @ApiModelProperty(value = "Logradouro do endereço.")
    private String logradouro;

    @ApiModelProperty(value = "Número do endereço.")
    private String numero;

}
