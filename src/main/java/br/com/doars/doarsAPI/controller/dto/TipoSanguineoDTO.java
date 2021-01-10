package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.TipoSanguineo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TipoSanguineoDTO {

    @ApiModelProperty(value = "Identificador interno do tipo sanguineo.")
    private Long id;

    @ApiModelProperty(value = "Descrição do tipo sanguineo.")
    private String descricao;

    public TipoSanguineoDTO(TipoSanguineo tipoSanguineo) {
        this.id = tipoSanguineo.getId();
        this.descricao = tipoSanguineo.getDescricao();
    }

    public static List<TipoSanguineoDTO> converterMotelToDTO(List<TipoSanguineo> tipoSanguineos){
        return tipoSanguineos.stream().map(TipoSanguineoDTO::new).collect(Collectors.toList());
    }

}
