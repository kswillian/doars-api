package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class EmailForm {

    @NotEmpty
    @ApiModelProperty(value = "Nome")
    private String nome;

    @NotEmpty
    @Email
    @ApiModelProperty(value = "E-mail")
    private String email;

    @NotEmpty
    @Email
    @ApiModelProperty(value = "Assunto do E-mail")
    private String assunto;

    @NotEmpty
    @ApiModelProperty(value = "Corpo do E-mail")
    private String mensagem;

}
