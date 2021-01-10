package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class TipoSanguineoFormUpdate {

    @NotNull
    @ApiModelProperty(value = "Identificador interno do tipo sanguineo.")
    private Long id;

    @NotNull
    @Length(min = 2, max = 3)
    @ApiModelProperty(value = "Descrição do tipo sanguineo.")
    private String descricao;

}
