package br.com.doars.doarsAPI.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @ApiModelProperty(value = "E-mail válido para contato.")
    private String email;

    @ApiModelProperty(value = "Número de celular.")
    private String celular;

    @ApiModelProperty(value = "Número de telefone fixo.")
    private String telefone;

}
