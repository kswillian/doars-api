package br.com.doars.doarsAPI.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoadoresTipoSanguineoDTO {

    private TipoSanguineoDTO tipoSanguineo;
    private Long numeroDoadores;

}
