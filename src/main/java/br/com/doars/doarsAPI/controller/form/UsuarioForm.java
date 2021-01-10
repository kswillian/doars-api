package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UsuarioForm {

    @NotNull
    @Length(min = 5, max = 150)
    @ApiModelProperty(value = "Email para acesso na plataforma.")
    private String email;

    @NotNull
    @Length(min = 7, max = 150)
    @ApiModelProperty(value = "Senha para acesso na plataforma.")
    private String senha;

    @NotNull
    @ApiModelProperty(value = "Perfil de usuário.")
    private String perfil;

}
