package br.com.doars.doarsAPI.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitacaoDoacaoTipoSanguineoDTO {

    private TipoSanguineoDTO tipoSanguineo;
    private Long numeroSolicitacoes;

}
