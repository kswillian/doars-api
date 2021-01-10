package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginForm {

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "Email do usuário.")
    private String email;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "Senha do usuário.")
    private String senha;

}
