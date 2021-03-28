package br.com.doars.doarsAPI.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardDoadoresTipoSanguineoDTO {

    private List<DoadoresTipoSanguineoDTO> doadoresTipoSanguineo;
    private String dataAtualizacao;

}
