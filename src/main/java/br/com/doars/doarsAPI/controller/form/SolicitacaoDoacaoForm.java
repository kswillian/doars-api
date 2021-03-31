package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SolicitacaoDoacaoForm {

    @NotNull
    @ApiModelProperty(value = "Identificador interno da entidade.")
    private Long idEntidade;

    @NotEmpty
    @ApiModelProperty(value = "Lista dos identificadores internos dos tipos sanguineos.")
    private List<Long> tiposSanguineos;

    private String descricao;

    @NotNull
    @ApiModelProperty(value = "Distância definida para notificar doadores")
    private Long distancia;

}
