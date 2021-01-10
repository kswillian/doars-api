package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Entidade;
import br.com.doars.doarsAPI.domain.enuns.TipoEntidade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class EntidadeSimpleDTO {

    @ApiModelProperty(value = "Identificador interno da entidade.")
    private Long id;

    @ApiModelProperty(value = "Nome da entidade.")
    private String nome;

    @ApiModelProperty(value = "Tipo de entidade.")
    private TipoEntidade tipoEntidade;

    public EntidadeSimpleDTO(Entidade entidade) {
        this.id = entidade.getId();
        this.nome = entidade.getNome();
        this.tipoEntidade = entidade.getTipoEntidade();
    }

}
