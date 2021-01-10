package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ContatoForm {

    @Email
    @NotNull
    @ApiModelProperty(value = "Email válido para contato.")
    private String email;

    @ApiModelProperty(value = "Número de celular.")
    private String celular;

    @ApiModelProperty(value = "Número de telefone fixo.")
    private String telefone;

}
