package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class RecuperacaoSenhaUsuarioForm {

    @NotNull
    @ApiModelProperty(value = "Email do usuário.")
    private String email;

    @NotNull
    @Length(min = 7, max = 150)
    @ApiModelProperty(value = "Nova senha para alteração.")
    private String novaSenha;

}
