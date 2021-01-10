package br.com.doars.doarsAPI.controller.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SolicitacaoDoacaoForm {

    @NotNull
    @ApiModelProperty(value = "Identificador interno da entidade.")
    private Long idEntidade;

    @NotNull
    @ApiModelProperty(value = "Lista dos identificadores internos dos tipos sanguineos.")
    private List<Long> tipoSanguineosList;

    private String descricao;

    @NotNull
    @ApiModelProperty(value = "Distância definida para notificar doadores")
    private Long distancia;

}
