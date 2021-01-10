package br.com.doars.doarsAPI.controller.form;

import br.com.doars.doarsAPI.domain.enuns.Sexo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class DoadorForm {

    @NotNull
    @ApiModelProperty(value = "Nome do doador.")
    private String nome;

    @NotNull
    @ApiModelProperty(value = "Sexo do doador.")
    private Sexo sexo;

    @Valid
    @ApiModelProperty(value = "Endereço do doador.")
    private EnderecoForm endereco;

    @Valid
    @ApiModelProperty(value = "Contato do doador.")
    private ContatoForm contato;

    @NotNull
    @ApiModelProperty(value = "Indica se dodoador é um doador.")
    private Boolean ehDoador;

    @NotNull
    @ApiModelProperty(value = "Identificador interno do tipo sanguineo.")
    private Long idTipoSanguineo;

}
