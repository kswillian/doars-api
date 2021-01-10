package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AutenticacaoUsuarioForm {

    @NotNull
    @ApiModelProperty(value = "Email do usuário.")
    private String email;

    @NotNull
    @ApiModelProperty(value = "Código de ativação.")
    private String codigo;


}
