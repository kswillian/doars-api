package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PerfilForm {

    @NotNull
    @ApiModelProperty(value = "Descrição do perfil.")
    private String descricao;

}
